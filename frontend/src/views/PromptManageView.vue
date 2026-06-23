<template>
  <div class="prompt-manage">
    <div class="pm-header">
      <h2>Prompt 模板管理</h2>
      <div class="pm-header-right">
        <router-link to="/" class="back-link">
          <el-icon><ArrowLeft /></el-icon> 返回对话
        </router-link>
        <el-button type="primary" @click="handleAdd">新增模板</el-button>
      </div>
    </div>

    <el-table :data="prompts" stripe style="width:100%">
      <el-table-column prop="name" label="名称" width="140" />
      <el-table-column prop="category" label="分类" width="80" />
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
      <el-table-column prop="matchKeywords" label="关键词" width="180" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.isActive" type="success" size="small">启用</el-tag>
          <el-tag v-else type="info" size="small">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button
            link type="danger" size="small"
            :disabled="row.isPreset"
            @click="handleDelete(row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <PromptFormDialog
      v-model:visible="dialogVisible"
      :edit-data="editData"
      @save="handleSave"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import PromptFormDialog from '../components/prompt/PromptFormDialog.vue'
import * as promptApi from '../api/promptApi'

const prompts = ref([])
const dialogVisible = ref(false)
const editData = ref(null)

onMounted(() => loadPrompts())

async function loadPrompts() {
  const res = await promptApi.listPrompts()
  prompts.value = res.data || []
}

function handleAdd() {
  editData.value = null
  dialogVisible.value = true
}

function handleEdit(row) {
  editData.value = { ...row }
  dialogVisible.value = true
}

async function handleSave(form) {
  if (editData.value?.id) {
    await promptApi.updatePrompt(editData.value.id, form)
    ElMessage.success('更新成功，即时生效')
  } else {
    await promptApi.createPrompt(form)
    ElMessage.success('创建成功，即时生效')
  }
  dialogVisible.value = false
  await loadPrompts()
}

async function handleDelete(row) {
  if (row.isPreset) {
    ElMessage.warning('预设模板不可删除')
    return
  }
  await ElMessageBox.confirm('确定删除此模板？', '确认', { type: 'warning' })
  await promptApi.deletePrompt(row.id)
  ElMessage.success('删除成功')
  await loadPrompts()
}
</script>

<style scoped>
.prompt-manage {
  max-width: 1000px;
  margin: 0 auto;
  padding: 24px;
}

.pm-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.pm-header h2 {
  font-size: 20px;
  font-weight: 600;
}

.pm-header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.back-link {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #606266;
  text-decoration: none;
  font-size: 14px;
}

.back-link:hover { color: #409eff; }
</style>
