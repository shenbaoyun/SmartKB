<template>
  <div class="conversation-sidebar">
    <div
      v-for="conv in conversations"
      :key="conv.id"
      class="conv-item"
      :class="{ active: conv.id === activeId }"
      @click="$emit('select', conv)"
    >
      <div class="conv-title">{{ conv.title }}</div>
      <div class="conv-preview">{{ conv.lastMessage || '暂无消息' }}</div>
      <el-dropdown trigger="click" class="conv-menu" @command="(cmd) => handleCommand(cmd, conv)">
        <el-icon :size="14"><MoreFilled /></el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="delete">删除</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <div v-if="conversations.length === 0" class="empty-hint">暂无对话</div>
  </div>
</template>

<script setup>
import { MoreFilled } from '@element-plus/icons-vue'

const props = defineProps({
  conversations: { type: Array, default: () => [] },
  activeId: { type: Number, default: null }
})

const emit = defineEmits(['select', 'delete'])

function handleCommand(cmd, conv) {
  if (cmd === 'delete') emit('delete', conv)
}
</script>

<style scoped>
.conversation-sidebar {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.conv-item {
  padding: 10px 12px;
  border-radius: 8px;
  cursor: pointer;
  position: relative;
  margin-bottom: 4px;
}

.conv-item:hover { background: #f0f2f5; }
.conv-item.active { background: #ecf5ff; }

.conv-title {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  margin-bottom: 4px;
  padding-right: 24px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.conv-preview {
  font-size: 12px;
  color: #909399;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.conv-menu {
  position: absolute;
  right: 8px;
  top: 12px;
  color: #909399;
  cursor: pointer;
}

.empty-hint {
  text-align: center;
  color: #c0c4cc;
  padding: 40px 0;
  font-size: 13px;
}
</style>
