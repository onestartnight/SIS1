<template>
  <div>
    <h1>学生信息系统</h1>
    <div style="margin-bottom: 16px;">
      当前身份：
      <select v-model="role" @change="logout">
        <option value="student">学生</option>
        <option value="admin">管理员</option>
      </select>
    </div>

    <LoginPanel v-if="!loggedIn"
      :role="role"
      @login-success="onLoginSuccess"
    />

    <template v-else>
      <button @click="logout" style="margin-bottom: 1em">退出登录</button>
      <StudentDashboard
        :role="role"
        :students="students"
        :current-student-id="currentStudentId"
        :current-student-number="currentStudentNumber"
        @refresh="fetchStudents"
      />
    </template>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import StudentDashboard from './views/StudentDashboard.vue'
import LoginPanel from './views/LoginPanel.vue'
import { getStudents, getStudent } from './api/student'

const role = ref('student')
const loggedIn = ref(false)
const students = ref([])
const currentStudentId = ref('')
const currentStudentNumber = ref('')

async function fetchStudents() {
  if (role.value === 'admin') {
    const res = await getStudents()
    students.value = res.data
  } else if (role.value === 'student' && currentStudentNumber.value) {
    const res = await getStudent(currentStudentNumber.value)
    students.value = res.data ? [res.data] : []
    if (students.value.length) currentStudentId.value = students.value[0].id
  }
}

function onLoginSuccess({ username, role: r }) {
  loggedIn.value = true
  if (r === 'student') {
    currentStudentNumber.value = username
  }
  fetchStudents()
}

function logout() {
  loggedIn.value = false
  currentStudentId.value = ''
  currentStudentNumber.value = ''
  students.value = []
}
</script>