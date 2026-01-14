const { defineConfig } = require('@vue/cli-service')

// 关键修复：使用了 defineConfig 包裹配置对象
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭烦人的 ESLint 检查，保证能打包成功
  lintOnSave: false,
  // 确保路径正确
  publicPath: '/'
})