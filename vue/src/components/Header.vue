<template>
    <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
        <div style="width: 200px; padding-left: 30px; font-weight: bold;color:dodgerblue">后台管理</div>
        <div style="flex: 1"></div>
        <div style="width: 100px">
            <el-dropdown>
                <span class="el-dropdown-link">
                  <div>
                    <el-avatar :size="30" :src="user.avatar" style="position: relative; top: 10px"></el-avatar>
                    {{ user.nickName }}
                    <el-icon>
                      <ArrowDown />
                    </el-icon>
                  </div>

                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
                        <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>


<script>
import {ArrowDown} from '@element-plus/icons-vue'
export default {
    name: "Header",
    components:{
      ArrowDown,
    },
    setup(){
      return {ArrowDown,}
  },
  data() {
    return {
      user: {}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  },
}
</script>

<!--<script lang="ts" setup>-->
<!--import { ArrowDown } from '@element-plus/icons-vue'-->
<!--</script>-->

<style scoped>
/*.example-showcase .el-dropdown-link {*/
/*  cursor: pointer;*/
/*  color: var(--el-color-primary);*/
/*  display: flex;*/
/*  align-items: center;*/
/*}*/
</style>