<template>
  <el-select
    :model-value="modelCode"
    @update:model-value="$emit('update:modelCode', $event)"
    size="small"
    style="width: 160px"
    placeholder="选择模型"
  >
    <el-option
      v-for="m in models"
      :key="m.code"
      :label="m.name"
      :value="m.code"
    />
  </el-select>
</template>

<script setup>
defineProps({
  modelCode: { type: String, default: 'deepseek-v3' },
  models: { type: Array, default: () => [] }
})
defineEmits(['update:modelCode'])
</script>
