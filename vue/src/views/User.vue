<template>

  <div style="padding: 10px">
  <!-- 新增区域 -->
    <div>
<!--      @click="add"-->
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary" round>导入</el-button>
      <el-button type="primary" round>导出</el-button>
    </div>
  <!-- 搜索区域 -->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 200px"  clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load" >查询</el-button>
    </div>
    <el-table :data="tableData" stripe border style="width: 100%">
    <el-table-column prop="id" label="ID" sortable  />
    <el-table-column prop="username" label="用户名"  />
    <el-table-column prop="nickName" label="昵称" />
    <el-table-column prop="sex" label="性别" />
    <el-table-column prop="age" label="年龄" />
    <el-table-column prop="address" label="地址" />
    <el-table-column fixed="right" label="Operations" width="120">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="handleEdit(scope.row)"
      >编辑</el-button>
         <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
          <template #reference>
            <el-button type="text" size="small" >删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

   <el-pagination
      v-model:currentPage="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 15, 20]"
      :small="small"
      :disabled="disabled"
      :background="background"
      layout="sizes, prev, pager, next"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="form" label-width="120px" >
          <el-form-item label="用户名" >
            <el-input v-model="form.username" style="width: 80%" />
          </el-form-item>
          <el-form-item label="昵称" >
            <el-input v-model="form.nickName" style="width: 80%" />
          </el-form-item>
          <el-form-item label="性别" >
              <el-radio  v-model="form.sex" label="男" >男</el-radio>
              <el-radio  v-model="form.sex" label="女" >女</el-radio>
              <el-radio  v-model="form.sex" label="未知" >未知</el-radio>
          </el-form-item>
          <el-form-item label="年龄" >
            <el-input v-model="form.age" style="width: 80%" />
          </el-form-item>
          <el-form-item label="地址" >
            <el-input type="textarea" v-model="form.address" style="width: 80%" />
          </el-form-item>
        </el-form>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="save"
            >确定</el-button
            >
          </span>
        </template>
    </el-dialog>

  </div>
</template>

<script>
// // @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

import { method } from 'lodash'
import request from "../utils/request";




export default {
  name: "user",
  data() {
    return {
      form:{},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      total: 10,
      pageSize: 10,
      tableData: []
   }
  },
  created(){
    this.load()
  },
  methods: {
    load() {
      request.get("/user", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true

    },
    handleDelete(id){
        console.log(id)
        request.delete("user/" + id).then(res =>{
          if(res.code==='0'){
            this.$message(
                {
                  type: "success",
                  message: "删除成功"})
          }else{
            this.$message({
              type: "error",
              message: res.msg})
          }
          this.load()
        })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()

    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (this.form.id) {
        request.put("/user", this.form).then(res => {
          console.log(res)
          if(res.code==='0'){
            this.$message(
                {
                  type: "success",
                  message: "更新成功"})
          }else{
            this.$message({
                  type: "error",
                  message: res.msg})
            }
          this.load()
          this.dialogVisible = false
        })
      } else {
        request.post("/user", this.form).then(res => {
          console.log(res)
          if(res.code==='0'){
            this.$message(
                {
                  type: "success",
                  message: "新增成功"})
          }else{
            this.$message({
              type: "error",
              message: res.msg})
          }
          this.load()
          this.dialogVisible = false
        })
      }
    }
  }
}


</script>
