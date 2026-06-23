<template>
  <div class="prompt-selector">
    <el-select
      :model-value="promptId"
      @update:model-value="handleChange"
      size="small"
      style="width: 180px"
      placeholder="智能匹配"
      clearable
    >
      <el-option label="智能匹配" :value="null" />
      <el-option
        v-for="p in prompts"
        :key="p.id"
        :label="p.name"
        :value="p.id"
      >
        <span>{{ p.name }}</span>
        <span style="float:right;color:#909399;font-size:12px">{{ p.category }}</span>
      </el-option>
    </el-select>
    <el-tag v-if="!promptId" type="info" size="small" effect="plain">智能</el-tag>
    <el-tag v-else type="primary" size="small" effect="plain">锁定</el-tag>
  </div>
</template>

<script setup>
defineProps({
  promptId: { type: Number, default: null },
  prompts: { type: Array, default: () => [] }
})
const emit = defineEmits(['update:promptId', 'update:promptMode'])

function handleChange(val) {
  emit('update:promptId', val)
  emit('update:promptMode', val ? 'manual' : 'auto')
}
</script>

<style scoped>
.prompt-selector {
  display: flex;
  align-items: center;
  gap: 8px;
}
</style>
