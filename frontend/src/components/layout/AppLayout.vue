<template>
  <div class="app-layout">
    <aside class="sidebar">
      <div class="sidebar-header">
        <h2>SmartKB</h2>
        <el-button type="primary" size="small" @click="$emit('new-conversation')">
          <el-icon><Plus /></el-icon> 新对话
        </el-button>
      </div>
      <slot name="sidebar" />
      <div class="sidebar-footer">
        <router-link to="/documents" class="nav-link">
          <el-icon><Folder /></el-icon> 文档管理
        </router-link>
        <router-link to="/prompts" class="nav-link">
          <el-icon><Setting /></el-icon> Prompt 管理
        </router-link>
      </div>
    </aside>
    <main class="main-content">
      <slot />
    </main>
  </div>
</template>

<script setup>
import { Plus, Setting } from '@element-plus/icons-vue'
defineEmits(['new-conversation'])
</script>

<style scoped>
.app-layout {
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

.sidebar {
  width: 260px;
  min-width: 260px;
  background: #fff;
  border-right: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  padding: 16px;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.sidebar-header h2 {
  font-size: 18px;
  font-weight: 700;
  color: #409eff;
  margin: 0;
}

.sidebar-footer {
  padding: 12px 16px;
  border-top: 1px solid #e4e7ed;
  margin-top: auto;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #606266;
  text-decoration: none;
  padding: 8px 12px;
  border-radius: 6px;
  font-size: 14px;
}

.nav-link:hover {
  background: #f0f2f5;
  color: #409eff;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  background: #f5f7fa;
}
</style>
