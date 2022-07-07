<template>
  <div style="width: 100%; height: 100vh;background-color: darkslateblue;overflow: hidden;">
    <div style="width: 400px; margin: 150px auto;">
      <div style="color: #cccccc; text-align: center; font-size: 30px; padding:30px 0;" >
        欢迎注册
      </div>
      <el-form :model="form" size="normal" :rules="rules" ref="form">
        <el-form-item prop="username">
          <el-input v-model="form.username" :prefix-icon="Avatar" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" :prefix-icon="Lock" />
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input v-model="form.confirm" :prefix-icon="Lock" />
        </el-form-item>
        <el-button style="width: 100%;"  type="primary" @click="register">
          注册</el-button>
      </el-form>

    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import {Avatar, Lock} from "@element-plus/icons-vue";
export default {
  name: "register",
  data(){
    return  {
      form:{},
      rules:{
        username:[{required: true, message: '请输入用户名', trigger: 'blur' }],
        password:[{required: true, message: '请输入密码', trigger: 'blur' }],
        confirm:[{required: true, message: '请确认密码', trigger: 'blur' }],
      },
    }
  },
  setup(){
    return {
      Lock,
      Avatar,
    }
  },
  methods: {
    register() {
      if(this.form.password !== this.form.confirm){
        this.$message({
          type: "error",
          message:"两次密码输入不一致"})
        return
      }

      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log('submit!')
          request.post("/user/register", this.form).then(res => {
            console.log(res)
            if(res.code==='0'){
              this.$message(
                  {
                    type: "success",
                    message: "注册成功"})
              this.$router.push("/login") //登录成功后进行页面跳转，跳转至主页
            }
          })
        }else{
          console.log('error submit!')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>