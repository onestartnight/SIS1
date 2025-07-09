<template>
  <div>
    <h2>学生信息</h2>
    <div v-if="student">
      <div>姓名: {{ student.name }}</div>
      <div>学号: {{ student.id }}</div>
    </div>
    <div v-else>
      暂无学生信息
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const student = ref({
  name: '张三',
  id: '20250001'
})
</script>