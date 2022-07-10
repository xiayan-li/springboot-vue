<template>

  <div style="padding: 10px">
    <!-- 新增区域 -->
    <div>
      <!--      @click="add"-->
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <!-- 搜索区域 -->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%"  clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load" >查询</el-button>
    </div>
    <el-table :data="tableData" stripe border style="width: 100%">
      <el-table-column prop="id" label="ID" sortable  />
      <el-table-column prop="title" label="标题"  />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="time" label="发布时间" />

      <el-table-column fixed="right" label="Operations" width="200">
        <template #default="scope">
          <el-button size="mini" @click="details(scope.row)">详情</el-button>
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

    <el-dialog v-model="dialogVisible" title="Tips" width="40%">
      <el-form :model="form" label-width="120px" >
        <el-form-item label="标题" >
          <el-input v-model="form.title" style="width: 50%" />
        </el-form-item>
<!--        <div id="div1"></div>-->
        <div style="border: 1px solid #ccc">
          <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editorRef"
              :defaultConfig="toolbarConfig"
              :mode="mode"
          />
          <Editor
              style="height: 300px; overflow-y: hidden;"
              v-model="valueHtml"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="handleCreated"
          />
        </div>

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

    <el-dialog title="详情" v-model="vis" width="50%">
      <el-card>
        <div v-html="detail.content" style="min-height: 100px"></div>
      </el-card>
    </el-dialog>

  </div>
</template>

<script>
// // @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'


import request from "../utils/request";
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
// import { IEditorConfig} from '@wangeditor/editor'





export default {
  name: "news",
  components: { Editor, Toolbar},
  data() {
    return {
      author: "",
      form:{},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      total: 10,
      pageSize: 10,
      tableData: [],
      vis: false,
      detail: {} ,//这些返回的可以是属性p也可以是域f(只有在method里才可为f)
      user: {},
    }
  },
  created(){
    this.load()
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
    this.author = this.user.username
  },
  methods: {
    load() {
      request.get("/news", {
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
      request.delete("news/" + id).then(res =>{
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
      this.form.content = this.valueHtml
      if (this.form.id) {
        request.put("/news", this.form).then(res => {
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
        request.post("/news", this.form).then(res => {
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
    details(row) {
      this.detail = row
      this.vis = true

    },
  },
  setup() {
    // 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()

    // 内容 HTML
    const valueHtml = ref('<p>hello</p>')

    // 模拟 ajax 异步获取内容
    onMounted(() => {
      setTimeout(() => {
        valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
      }, 1500)
    })

    const toolbarConfig = {}

    const editorConfig = {MENU_CONF: {}}
    editorConfig.MENU_CONF['uploadImage'] = {
      server: '/api/files/editor/upload',
      fieldName: 'file'
    }


    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {
      const editor = editorRef.value
      if (editor == null) return
      editor.destroy()
    })

    const handleCreated = (editor) => {
      editorRef.value = editor // 记录 editor 实例，重要！
      console.log('created', editor)
    }

    return {
      editorRef,
      valueHtml,
      mode: 'default', // 或 'simple'
      toolbarConfig,
      editorConfig,
      handleCreated //这些返回的只可以是属性p
    };
  },


}


</script>
