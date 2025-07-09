import axios from 'axios'

const API_BASE = 'http://localhost:8080/api/students'

export function getStudents() {
  return axios.get(API_BASE)
}
export function getStudent(studentNumber) {
  return axios.get(`${API_BASE}/${studentNumber}`)
}
export function addOrUpdateStudent(student) {
  return axios.post(API_BASE, student)
}
export function deleteStudent(id) {
  return axios.delete(`${API_BASE}/${id}`)
}