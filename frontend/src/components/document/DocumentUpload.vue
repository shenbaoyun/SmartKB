<template>
  <div class="doc-upload">
    <el-upload
      ref="uploadRef"
      :auto-upload="false"
      :limit="1"
      accept=".pdf,.txt,.md,.docx"
      :on-change="handleChange"
      :show-file-list="false"
    >
      <el-button :icon="Upload" :loading="uploading" size="small">上传文档</el-button>
    </el-upload>
    <span v-if="fileName" class="file-name">{{ fileName }}</span>
    <el-button v-if="fileName" type="primary" size="small" :loading="uploading" @click="doUpload">确认上传</el-button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Upload } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { uploadDocument } from '../../api/documentApi'

const emit = defineEmits(['uploaded'])
const uploading = ref(false)
const fileName = ref('')
const selectedFile = ref(null)

function handleChange(file) {
  selectedFile.value = file.raw
  fileName.value = file.name
}

async function doUpload() {
  if (!selectedFile.value) return
  uploading.value = true
  try {
    await uploadDocument(selectedFile.value)
    ElMessage.success('Upload started, processing...')
    emit('uploaded')
    fileName.value = ''
    selectedFile.value = null
  } catch (e) {
    ElMessage.error('Upload failed')
  } finally {
    uploading.value = false
  }
}
</script>

<style scoped>
.doc-upload { display: flex; align-items: center; gap: 8px; }
.file-name { font-size: 13px; color: #606266; max-width: 150px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
</style>