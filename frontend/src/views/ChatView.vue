<template>
  <AppLayout @new-conversation="handleNewConversation">
    <template #sidebar>
      <ConversationSidebar
        :conversations="conversations"
        :active-id="activeConversation?.id"
        @select="handleSelectConversation"
        @delete="handleDeleteConversation"
      />
    </template>

    <div class="chat-container">
      <!-- 顶部操作栏 -->
      <div class="chat-header">
        <ModelSwitcher v-model:modelCode="currentModelCode" :models="models" />
        <PromptSelector
          v-model:promptId="currentPromptId"
          v-model:promptMode="currentPromptMode"
          :prompts="prompts"
        />
        <DocumentUpload @uploaded="() => {}" />
        <span class="header-title">{{ activeConversation?.title || '新对话' }}</span>
      </div>

      <!-- 消息区域 -->
      <ChatWindow
        :messages="messages"
        :streaming-content="streamingContent"
        :loading="loading"
      />

      <!-- 输入区域 -->
      <MessageInput
        :loading="loading"
        :current-prompt-name="currentPromptName"
        @send="handleSendMessage"
      />
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import AppLayout from '../components/layout/AppLayout.vue'
import ConversationSidebar from '../components/conversation/ConversationSidebar.vue'
import ChatWindow from '../components/chat/ChatWindow.vue'
import MessageInput from '../components/chat/MessageInput.vue'
import ModelSwitcher from '../components/model/ModelSwitcher.vue'
import PromptSelector from '../components/prompt/PromptSelector.vue'
import DocumentUpload from '../components/document/DocumentUpload.vue'
import * as conversationApi from '../api/conversationApi'
import * as chatApi from '../api/chatApi'
import * as promptApi from '../api/promptApi'
import * as modelApi from '../api/modelApi'

// 数据状态
const conversations = ref([])
const activeConversation = ref(null)
const messages = ref([])
const prompts = ref([])
const models = ref([])
const loading = ref(false)
const streamingContent = ref('')

// 当前选择的模型和 Prompt
const currentModelCode = ref('deepseek-v3')
const currentPromptId = ref(null)
const currentPromptMode = ref('auto')

const currentPromptName = computed(() => {
  if (currentPromptMode.value === 'auto') return null
  const p = prompts.value.find(pr => pr.id === currentPromptId.value)
  return p ? p.name : null
})

// 初始化加载
onMounted(async () => {
  await loadConversations()
  await loadPrompts()
  await loadModels()
})

async function loadConversations() {
  const res = await conversationApi.listConversations()
  conversations.value = res.data || []
}

async function loadPrompts() {
  const res = await promptApi.listPrompts()
  prompts.value = res.data || []
}

async function loadModels() {
  const res = await modelApi.listModels()
  models.value = res.data || []
}

async function loadMessages(conversationId) {
  const res = await conversationApi.getMessages(conversationId)
  messages.value = res.data || []
}

// 新建会话
async function handleNewConversation() {
  const res = await conversationApi.createConversation({
    title: '新对话',
    modelCode: currentModelCode.value,
    promptId: currentPromptId.value,
    promptMode: currentPromptMode.value
  })
  conversations.value.unshift(res.data)
  activeConversation.value = res.data
  messages.value = []
  streamingContent.value = ''
}

// 选择会话
async function handleSelectConversation(conv) {
  activeConversation.value = conv
  currentModelCode.value = conv.modelCode || 'deepseek-v3'
  currentPromptId.value = conv.promptId
  currentPromptMode.value = conv.promptMode || 'auto'
  streamingContent.value = ''
  await loadMessages(conv.id)
}

// 删除会话
async function handleDeleteConversation(conv) {
  await ElMessageBox.confirm('确定删除此会话？', '确认', { type: 'warning' })
  await conversationApi.deleteConversation(conv.id)
  conversations.value = conversations.value.filter(c => c.id !== conv.id)
  if (activeConversation.value?.id === conv.id) {
    activeConversation.value = null
    messages.value = []
  }
  ElMessage.success('已删除')
}

// 发送消息（SSE 流式）
async function handleSendMessage(content) {
  if (!activeConversation.value) await handleNewConversation()
  const convId = activeConversation.value.id

  // 添加用户消息到本地
  messages.value.push({ id: Date.now(), role: 'USER', content })
  loading.value = true
  streamingContent.value = ''

  const params = new URLSearchParams()
  params.append('content', content)
  params.append('promptMode', currentPromptMode.value)
  if (currentPromptId.value) params.append('promptId', currentPromptId.value)
  params.append('modelCode', currentModelCode.value)

  const eventSource = new EventSource(`/api/chat/${convId}/stream?${params}`)

  eventSource.addEventListener('message', (e) => {
    streamingContent.value += e.data
  })

  eventSource.addEventListener('done', (e) => {
    eventSource.close()
    const data = JSON.parse(e.data)
    messages.value.push({
      id: Date.now(),
      role: 'ASSISTANT',
      content: streamingContent.value,
      promptName: data.promptName
    })
    streamingContent.value = ''
    loading.value = false
    loadConversations() // 刷新会话列表
  })

  eventSource.addEventListener('error', () => {
    eventSource.close()
    loading.value = false
    if (streamingContent.value) {
      messages.value.push({
        id: Date.now(),
        role: 'ASSISTANT',
        content: streamingContent.value
      })
    }
    streamingContent.value = ''
    ElMessage.error('连接中断')
  })
}
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.chat-header {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 16px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
}

.header-title {
  font-size: 15px;
  font-weight: 500;
  color: #303133;
  margin-left: auto;
}
</style>
