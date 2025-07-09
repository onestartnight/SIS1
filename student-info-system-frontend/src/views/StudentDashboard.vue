<template>
  <div>
    <h2>学生信息</h2>
    <!-- 学生视图 -->
    <div v-if="role === 'student'">
      <div v-if="currentStudent">
        <div>姓名: {{ currentStudent.name }}</div>
        <div>学号: {{ currentStudent.student_number }}</div>
        <div>电话: {{ currentStudent.phone }}</div>
        <div>地址: {{ currentStudent.address }}</div>
        <div>Email: {{ currentStudent.email }}</div>
      </div>
      <div v-else>
        暂无学生信息
      </div>
    </div>

    <!-- 管理员视图 -->
    <div v-else>
      <table border="1" cellpadding="6" style="margin-bottom: 1em;">
        <thead>
          <tr>
            <th>姓名</th>
            <th>学号</th>
            <th>电话</th>
            <th>地址</th>
            <th>Email</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="stu in students" :key="stu.id">
            <td>
              <span v-if="editId !== stu.id">{{ stu.name }}</span>
              <input v-else v-model="editName" />
            </td>
            <td>
              <span v-if="editId !== stu.id">{{ stu.student_number }}</span>
              <input v-else v-model="editStudentNumber" />
            </td>
            <td>
              <span v-if="editId !== stu.id">{{ stu.phone }}</span>
              <input v-else v-model="editPhone" />
            </td>
            <td>
              <span v-if="editId !== stu.id">{{ stu.address }}</span>
              <input v-else v-model="editAddress" />
            </td>
            <td>
              <span v-if="editId !== stu.id">{{ stu.email }}</span>
              <input v-else v-model="editEmail" />
            </td>
            <td>
              <button v-if="editId !== stu.id" @click="startEdit(stu)">编辑</button>
              <button v-else @click="saveEdit(stu)">保存</button>
              <button v-if="editId === stu.id" @click="cancelEdit">取消</button>
              <button @click="remove(stu.id)" style="color:red;">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <form @submit.prevent="add">
        <input v-model="newName" placeholder="姓名" required />
        <input v-model="newStudentNumber" placeholder="学号" required />
        <input v-model="newPhone" placeholder="电话" />
        <input v-model="newAddress" placeholder="地址" />
        <input v-model="newEmail" placeholder="Email" />
        <button type="submit">添加学生</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { addOrUpdateStudent, deleteStudent } from '../api/student.js'

const props = defineProps({
  role: String,
  students: Array,
  currentStudentId: String,
  currentStudentNumber: String
})
const emit = defineEmits(['refresh'])

const newName = ref('')
const newStudentNumber = ref('')
const newPhone = ref('')
const newAddress = ref('')
const newEmail = ref('')

// 简单正则校验
function isValidEmail(email) {
  return !email || /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(email)
}
function isValidPhone(phone) {
  return !phone || /^1[3-9]\d{9}$/.test(phone)
}

async function add() {
  if (!isValidEmail(newEmail.value)) {
    alert('邮箱格式不正确')
    return
  }
  if (!isValidPhone(newPhone.value)) {
    alert('手机号格式不正确')
    return
  }
  try {
    await addOrUpdateStudent({
      name: newName.value,
      student_number: newStudentNumber.value,
      phone: newPhone.value,
      address: newAddress.value,
      email: newEmail.value
    })
    alert('操作成功')
    newName.value = ''
    newStudentNumber.value = ''
    newPhone.value = ''
    newAddress.value = ''
    newEmail.value = ''
    emit('refresh')
  } catch (err) {
    alert(err?.response?.data?.message || '操作失败，可能违背了数据库约束')
  }
}

async function remove(id) {
  if (confirm('确定删除该学生？')) {
    try {
      await deleteStudent(id)
      alert('操作成功')
      emit('refresh')
    } catch (err) {
      alert(err?.response?.data?.message || '操作失败')
    }
  }
}

// 编辑相关
const editId = ref('')
const editName = ref('')
const editStudentNumber = ref('')
const editPhone = ref('')
const editAddress = ref('')
const editEmail = ref('')

function startEdit(stu) {
  editId.value = stu.id
  editName.value = stu.name
  editStudentNumber.value = stu.student_number
  editPhone.value = stu.phone
  editAddress.value = stu.address
  editEmail.value = stu.email
}
async function saveEdit(origStu) {
  if (!editName.value || !editStudentNumber.value) return
  if (!isValidEmail(editEmail.value)) {
    alert('邮箱格式不正确')
    return
  }
  if (!isValidPhone(editPhone.value)) {
    alert('手机号格式不正确')
    return
  }
  try {
    await addOrUpdateStudent({
      id: origStu.id,
      name: editName.value,
      student_number: editStudentNumber.value,
      phone: editPhone.value,
      address: editAddress.value,
      email: editEmail.value
    })
    alert('操作成功')
    editId.value = ''
    emit('refresh')
  } catch (err) {
    alert(err?.response?.data?.message || '操作失败，可能违背了数据库约束')
  }
}
function cancelEdit() {
  editId.value = ''
}

// 计算当前学生
const currentStudent = computed(() =>
  props.students.find(stu => stu.id === props.currentStudentId)
)
</script>