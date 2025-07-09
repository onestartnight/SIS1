<template>
  <div style="margin: 2em 0">
    <form @submit.prevent="submit">
      <div v-if="role === 'student'">
        <label>学号：</label>
        <input v-model="username" placeholder="请输入学号" required>
        <label>密码：</label>
        <input v-model="password" type="password" placeholder="请输入密码" required>
      </div>
      <div v-else>
        <label>管理员账号：</label>
        <input v-model="username" placeholder="请输入管理员账号" required>
        <label>密码：</label>
        <input v-model="password" type="password" placeholder="请输入密码" required>
      </div>
      <button type="submit">登录</button>
    </form>
    <div v-if="errorMsg" style="color: red; margin-top: 1em">{{ errorMsg }}</div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { login } from '../api/auth'

const props = defineProps({ role: String })
const emit = defineEmits(['login-success'])

const username = ref('')
const password = ref('')
const errorMsg = ref('')

watch(() => props.role, () => {
  username.value = ''
  password.value = ''
  errorMsg.value = ''
})

async function submit() {
  errorMsg.value = ''
  try {
    const res = await login({ username: username.value, password: password.value })
    if (res.data.success) {
      emit('login-success', { username: res.data.username, role: res.data.role })
    } else {
      errorMsg.value = res.data.message || '登录失败'
    }
  } catch (e) {
    errorMsg.value = '网络错误或服务器无响应'
  }
}
</script>