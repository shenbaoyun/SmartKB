<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    :title="isEdit ? '编辑 Prompt 模板' : '新增 Prompt 模板'"
    width="560px"
    :close-on-click-modal="false"
  >
    <el-form :model="form" label-width="90px">
      <el-form-item label="名称">
        <el-input v-model="form.name" placeholder="如：Java 专家" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.category" style="width:100%">
          <el-option label="对话" value="对话" />
          <el-option label="编程" value="编程" />
          <el-option label="翻译" value="翻译" />
          <el-option label="办公" value="办公" />
          <el-option label="自定义" value="自定义" />
        </el-select>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" placeholder="简要说明适用场景" />
      </el-form-item>
      <el-form-item label="系统提示词">
        <el-input v-model="form.systemPrompt" type="textarea" :rows="6" placeholder="系统提示词，即注入到 AI 的 SystemMessage..." />
      </el-form-item>
      <el-form-item label="关键词">
        <el-input v-model="form.matchKeywords" placeholder="逗号分隔，如：Java,Spring,JVM,多线程" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="$emit('update:visible', false)">取消</el-button>
      <el-button type="primary" @click="$emit('save', form)">保存（即时生效）</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, watch, computed } from 'vue'

const props = defineProps({
  visible: { type: Boolean, default: false },
  editData: { type: Object, default: null }
})

const emit = defineEmits(['update:visible', 'save'])

const isEdit = computed(() => !!props.editData?.id)

const form = reactive({
  name: '',
  description: '',
  systemPrompt: '',
  category: '自定义',
  matchKeywords: ''
})

watch(() => props.editData, (val) => {
  if (val) {
    Object.assign(form, {
      name: val.name || '',
      description: val.description || '',
      systemPrompt: val.systemPrompt || '',
      category: val.category || '自定义',
      matchKeywords: val.matchKeywords || ''
    })
  } else {
    Object.assign(form, {
      name: '', description: '', systemPrompt: '', category: '自定义', matchKeywords: ''
    })
  }
}, { immediate: true })
</script>
