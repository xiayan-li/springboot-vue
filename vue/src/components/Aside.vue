<template>
    <div>
        <el-menu style="width: 200px; min-height: calc(100vh - 50px);"
                 router
                 default-active="$route.path.substr(1)"
                 class="el-menu-vertical-demo">
            <el-sub-menu index="1">
                <template #title>
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
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
    } from '@element-plus/icons-vue'

export default {
        name: "Aside",
        components:{
        Location,
        IconMenu,
        Document,
        Setting,
        }
  }
</script>


<style scoped>


</style>