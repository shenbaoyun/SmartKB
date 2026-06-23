<template>
  <div class="message-input">
    <div class="input-row">
      <el-input
        v-model="inputText"
        type="textarea"
        :rows="2"
        placeholder="输入消息..."
        resize="none"
        @keydown.enter.exact.prevent="handleSend"
        :disabled="loading"
      />
      <el-button
        type="primary"
        :icon="Promotion"
        @click="handleSend"
        :loading="loading"
        :disabled="!inputText.trim()"
      >
        发送
      </el-button>
    </div>
    <div class="input-hint">
      按 Enter 发送 · 当前 Prompt：<span class="current-prompt">{{ currentPromptName || '智能匹配' }}</span>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Promotion } from '@element-plus/icons-vue'

const props = defineProps({
  loading: { type: Boolean, default: false },
  currentPromptName: { type: String, default: null }
})

const emit = defineEmits(['send'])
const inputText = ref('')

function handleSend() {
  const text = inputText.value.trim()
  if (!text || props.loading) return
  emit('send', text)
  inputText.value = ''
}
</script>

<style scoped>
.message-input {
  padding: 12px 16px;
  background: #fff;
  border-top: 1px solid #e4e7ed;
}

.input-row {
  display: flex;
  gap: 10px;
  align-items: flex-end;
}

.input-hint {
  font-size: 12px;
  color: #909399;
  margin-top: 6px;
  text-align: right;
}

.current-prompt {
  color: #409eff;
  font-weight: 500;
}
</style>
