import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src'),
            '@components': path.resolve(__dirname, './src/components'),
            '@views': path.resolve(__dirname, './src/views')
        }
    },
    server: {
        host: '0.0.0.0',
        port: 5173,
        open: true,//服务启动时自动在浏览器中打开应用
        // http://localhost:3030/api/user的请求都会变成 http://localhost:8080/user
        proxy: {
            '/apis': {
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/apis/, ''),
            }
        },
    }
})
