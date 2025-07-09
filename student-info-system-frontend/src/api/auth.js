import axios from 'axios'

const API_BASE = 'http://localhost:8080/api/auth'

export function login({ username, password }) {
  return axios.post(`${API_BASE}/login`, { username, password })
}