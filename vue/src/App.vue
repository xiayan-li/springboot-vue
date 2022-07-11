<template>
  <!-- 要在App.vue要加el-config-provider，不然会出现组件无法渲染的警告-->
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>

</template>


<script>
import { ElConfigProvider } from 'element-plus'

import zhCn from 'element-plus/lib/locale/lang/zh-cn'

export default {
  name: "App",
  components: {
    [ElConfigProvider.name]: ElConfigProvider,
  },
  data() {
    return {
      locale: zhCn,
    }
  },
}
</script>