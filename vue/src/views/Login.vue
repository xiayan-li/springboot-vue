<template>
  <div style="width: 100%; height: 100vh;background-color: darkslateblue;overflow: hidden;">
    <div style="width: 400px; margin: 150px auto;">
      <div style="color: #cccccc; text-align: center; font-size: 30px; padding:30px 0;" >
        欢迎登录
      </div>
      <el-form :model="form" size="normal" :rules="rules" ref="form">
        <el-form-item prop="username">
          <el-input v-model="form.username" :prefix-icon="Avatar" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" :prefix-icon="Lock" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.validCode" style="width: 50%; padding-right: 5px" :prefix-icon="Key"></el-input>
          <ValidCode @input="createValidCode"></ValidCode>
        </el-form-item>
        <el-form-item>
          <el-radio v-model="form.role" :label="1">管理员</el-radio>
          <el-radio v-model="form.role" :label="2">普通用户</el-radio>
        </el-form-item>
        <el-button style="width: 100%;"  type="primary" @click="login()">
          登录</el-button>
      </el-form>

    </div>
  </div>

</template>

<script>
import  { Lock, Avatar, Key} from '@element-plus/icons-vue'
import request from "../utils/request";
import ValidCode from "@/components/ValidCode"
export default {
  name: "login",
  components:{ValidCode},
  data(){
    return  {
      form:{},
      rules:{
        username:[{required: true, message: '请输入用户名', trigger: 'blur' }],
        password:[{required: true, message: '请输入密码', trigger: 'blur' }],
        validCode:[{required: true, message: '请输入验证码', trigger: 'blur' }],
      },
      validCode:'',
    }
    // sessionStorage.removeItem("user")
  },
  setup(){
    return {
      Lock,
      Avatar,
      Key
    }
  },
  methods: {
    createValidCode(data){
      this.validCode = data
    },
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if(!this.form.validCode){
            this.$message.error("请填写验证码")
            return
          }
          if(this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()){
            this.$message.error("验证码错误")
            return
          }
          console.log('submit!')
          request.post("/user/login", this.form).then(res => {
            console.log(res)
            if (res.code === '0') {
              this.$message(
                  {
                    type: "success",
                    message: "登录成功"
                  })
              sessionStorage.setItem('user',JSON.stringify(res.data))
              this.$router.push("/") //登录成功后进行页面跳转，跳转至主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        } else {
          console.log('error submit!')
        }
      })

    }
  }
}
</script>

<style scoped>
.ValidCode {
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.ValidCode span{
  display: inline-block;
}
</style>