<template>

  <div style="padding: 10px">
    <!-- 新增区域 -->
    <div style="margin: 10px 0" >
      <!--      @click="add"-->
      <el-button type="primary" @click="add" v-if="user.role === 1">新增</el-button>
      <el-popconfirm
          title="确定删除吗？"
          @confirm="deleteBatch"
      >
        <template #reference>
          <el-button type="danger" v-if="user.role === 1">批量删除</el-button>
        </template>
      </el-popconfirm>
    </div>
    <!-- 搜索区域 -->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 200px"  clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load" >查询</el-button>
    </div>
    <el-table :data="tableData" stripe border style="width: 100%"  v-loading="loading" @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" sortable  />
      <el-table-column prop="name" label="书名"  />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="time" label="出版时间" />
      <el-table-column label="封面" >
        <template #default="scope">
          <el-image style="width: 100px; height: 100px"
                    :src="scope.row.cover"
                    :preview-src-list="[scope.row.cover]"
                    :fit="fit" />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="Operations" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)" v-if="user.role === 1">编辑</el-button>
          <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)" v-if="user.role === 1">
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
        <el-form-item label="书名" >
          <el-input v-model="form.name" style="width: 80%" />
        </el-form-item>
        <el-form-item label="价格" >
          <el-input v-model="form.price" style="width: 80%" />
        </el-form-item>
        <el-form-item label="作者" >
          <el-input v-model="form.author" style="width: 80%" />
        </el-form-item>
        <el-form-item label="出版时间" >
          <el-date-picker v-model="form.time" type="date" value-format="YYYY-MM-DD" placeholder="Pick a Date" style="width: 80%" clearable/>
        </el-form-item>
        <el-form-item label="封面">
          <el-upload
              ref="upload"
              action="/api/files/upload"
              :on-success="fileUploadSuccess"
          >
            <el-button type="primary">点击上传</el-button>
          </el-upload>
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
  name: "book",
  data() {
    return {
      form:{},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      total: 10,
      pageSize: 10,
      tableData: [],
      user: {},
      loading: true, //会有加载小圈圈
      ids:[]
    }
  },
  created(){
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    // 请求服务端，确认当前登录用户的 合法信息
    request.get("/user/" + this.user.id).then(res => {
      if (res.code === '0') {
        this.user = res.data
      }
    })
    this.load() //刚进来book/时进行页面加载
  },
  methods: {
    load() {
      this.loading = true
      request.get("/book", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() =>{
        if (this.$refs['upload']) {
          this.$refs['upload'].clearFiles()  // 清除历史文件列表
        }
      })
    },
    handleDelete(id){
      console.log(id)
      request.delete("book/" + id).then(res =>{
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
      if (this.$refs['upload']) {
        this.$refs['upload'].clearFiles()
      } //清除历史文件列表
    },
    save() {
      if (this.form.id) {
        request.put("/book", this.form).then(res => {
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
        request.post("/book", this.form).then(res => {
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
    },
    fileUploadSuccess(res){
      console.log(res)
      this.form.cover = res.data
    },
    deleteBatch() {
      if (!this.ids.length) {
        this.$message.warning("请选择数据！")
        return
      }
      request.post("/book/deleteBatch", this.ids).then(res => {
        if (res.code === '0') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleSelectionChange(val) {
      this.ids = val.map(v => v.id)
    },
  }
}


</script>
