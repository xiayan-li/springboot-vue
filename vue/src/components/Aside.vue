<template>
    <div>
        <el-menu style="width: 200px; min-height: calc(100vh - 50px);"
                 router
                 default-active="$route.path.substr(1)"
                 class="el-menu-vertical-demo">
          <el-sub-menu index="1" v-if="user.role === 1">
                <template #title >
                    <span>系统管理</span>
                </template>
                <el-menu-item index="user">用户管理</el-menu-item>
            </el-sub-menu>
            <el-menu-item index="book">书籍管理</el-menu-item>
            <el-menu-item index="news">新闻管理</el-menu-item>

        </el-menu>
    </div>

</template>


<script>
import {Document} from '@element-plus/icons-vue'
import {Menu as IconMenu,} from '@element-plus/icons-vue'
import {Location} from '@element-plus/icons-vue'
import {Setting} from '@element-plus/icons-vue'
// import {
//     Document,
//     Menu as IconMenu,
//     Location,
//     Setting,
//     } from '@element-plus/icons-vue'
import request from "@/utils/request";
export default {
        name: "Aside",
        components:{
        Location,
        IconMenu,
        Document,
        Setting,
        },
        data() {
          return{
            user: {},
            // path: this.$route.path   // 设置默认高亮的菜单
          }
        },
        created() {
          let userStr = sessionStorage.getItem("user") || "{}"
          this.user = JSON.parse(userStr)

          // 请求服务端，确认当前登录用户的 合法信息
          request.get("/user/" + this.user.id).then(res => {
            if (res.code === '0') {
              this.user = res.data
            }
          })
        },
  }
</script>


<style scoped>


</style>