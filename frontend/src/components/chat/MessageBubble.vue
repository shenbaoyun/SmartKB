<template>
  <div class="message-bubble" :class="role">
    <div v-if="role === 'ASSISTANT'" class="avatar ai-avatar">AI</div>
    <div class="bubble-content">
      <div class="bubble-meta" v-if="role === 'ASSISTANT' && promptName">
        <span class="prompt-tag">{{ promptName }}</span>
      </div>
      <div class="bubble-text" v-html="renderedContent" />
    </div>
    <div v-if="role === 'USER'" class="avatar user-avatar">我</div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  role: { type: String, required: true },
  content: { type: String, default: '' },
  promptName: { type: String, default: null }
})

/** Markdown 简单转 HTML */
const renderedContent = computed(() => {
  let text = props.content || ''
  text = text.replace(/"/g, '&quot;')
  text = text.replace(/`([^`]+)`/g, '<code>$1</code>')
  text = text.replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>')
  text = text.replace(/\n/g, '<br>')
  return text
})
</script>

<style scoped>
.message-bubble {
  display: flex;
  gap: 10px;
  padding: 8px 16px;
  max-width: 85%;
}

.message-bubble.USER {
  align-self: flex-end;
  flex-direction: row;
}

.message-bubble.ASSISTANT {
  align-self: flex-start;
  flex-direction: row;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 600;
  flex-shrink: 0;
}

.ai-avatar { background: #409eff; color: #fff; }
.user-avatar { background: #67c23a; color: #fff; }

.bubble-content {
  max-width: 100%;
}

.bubble-meta {
  margin-bottom: 4px;
}

.prompt-tag {
  font-size: 11px;
  color: #409eff;
  background: #ecf5ff;
  padding: 2px 8px;
  border-radius: 4px;
}

.bubble-text {
  font-size: 14px;
  line-height: 1.7;
  color: #303133;
  white-space: pre-wrap;
  word-break: break-word;
}

.bubble-text :deep(code) {
  background: #f0f2f5;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 13px;
  font-family: 'JetBrains Mono', 'Fira Code', monospace;
}

.bubble-text :deep(strong) {
  font-weight: 600;
}
</style>
