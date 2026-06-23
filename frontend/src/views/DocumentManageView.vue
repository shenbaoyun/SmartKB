<template>
  <div class="doc-manage">
    <div class="dm-header">
      <h2>Knowledge Documents</h2>
      <div class="dm-header-right">
        <router-link to="/" class="back-link"><el-icon><ArrowLeft /></el-icon> Back</router-link>
      </div>
    </div>

    <DocumentUpload @uploaded="loadDocs" style="margin-bottom:12px" />

    <el-table :data="docs" stripe style="width:100%">
      <el-table-column prop="name" label="File" show-overflow-tooltip />
      <el-table-column prop="fileType" label="Type" width="80" />
      <el-table-column label="Size" width="100">
        <template #default="{ row }">{{ formatSize(row.fileSize) }}</template>
      </el-table-column>
      <el-table-column prop="chunkCount" label="Chunks" width="80" />
      <el-table-column label="Status" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'READY'" type="success" size="small">Ready</el-tag>
          <el-tag v-else-if="row.status === 'PROCESSING'" type="warning" size="small">Processing</el-tag>
          <el-tag v-else type="danger" size="small">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="100">
        <template #default="{ row }">
          <el-button link type="danger" size="small" @click="handleDelete(row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="docs.length === 0" class="empty-hint">No documents uploaded</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ArrowLeft } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DocumentUpload from '../components/document/DocumentUpload.vue'
import { listDocuments, deleteDocument } from '../api/documentApi'

const docs = ref([])

onMounted(() => loadDocs())

async function loadDocs() {
  const res = await listDocuments()
  docs.value = res.data || []
}

function formatSize(bytes) {
  if (!bytes) return '0 B'
  if (bytes < 1024) return bytes + ' B'
  if (bytes < 1048576) return (bytes / 1024).toFixed(1) + ' KB'
  return (bytes / 1048576).toFixed(1) + ' MB'
}

async function handleDelete(row) {
  await ElMessageBox.confirm('Delete this document?', 'Confirm', { type: 'warning' })
  await deleteDocument(row.id)
  ElMessage.success('Deleted')
  loadDocs()
}
</script>

<style scoped>
.doc-manage { max-width: 1000px; margin: 0 auto; padding: 24px; }
.dm-header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 20px; }
.dm-header h2 { font-size: 20px; font-weight: 600; }
.dm-header-right { display: flex; align-items: center; gap: 16px; }
.back-link { display: flex; align-items: center; gap: 4px; color: #606266; text-decoration: none; font-size: 14px; }
.back-link:hover { color: #409eff; }
.empty-hint { text-align: center; color: #c0c4cc; padding: 40px 0; font-size: 13px; }
</style>