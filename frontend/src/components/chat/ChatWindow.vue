<template>
  <div class="chat-window" ref="chatWindowRef">
    <MessageBubble
      v-for="msg in messages"
      :key="msg.id"
      :role="msg.role"
      :content="msg.content"
      :prompt-name="msg.promptName"
    />
    <!-- 流式加载中的临时气泡 -->
    <div v-if="streamingContent" class="message-bubble ASSISTANT" style="display:flex;gap:10px;padding:8px 16px;max-width:85%">
      <div class="avatar ai-avatar">AI</div>
      <div class="bubble-text" v-html="streamingHtml" />
    </div>
    <div v-if="loading && !streamingContent" class="loading-hint">
      <el-icon class="is-loading"><Loading /></el-icon> 思考中...
    </div>
  </div>
</template>

<script setup>
import { computed, watch, nextTick, ref } from 'vue'
import { Loading } from '@element-plus/icons-vue'
import MessageBubble from './MessageBubble.vue'

const props = defineProps({
  messages: { type: Array, default: () => [] },
  streamingContent: { type: String, default: '' },
  loading: { type: Boolean, default: false }
})

const chatWindowRef = ref(null)

const streamingHtml = computed(() => {
  return (props.streamingContent || '').replace(/\n/g, '<br>')
})

watch(
  () => [props.messages.length, props.streamingContent],
  async () => {
    await nextTick()
    if (chatWindowRef.value) {
      chatWindowRef.value.scrollTop = chatWindowRef.value.scrollHeight
    }
  },
  { deep: true, immediate: true }
)
</script>

<style scoped>
.chat-window {
  flex: 1;
  overflow-y: auto;
  padding: 16px 0;
  display: flex;
  flex-direction: column;
}

.loading-hint {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 16px;
  color: #909399;
  font-size: 14px;
  align-self: flex-start;
}
</style>
