<script setup>

defineProps({
    className: {
        type: String,
        default: 'playedu'
    }
})
</script>

<template>
    <i class="iconfont" :class="className"></i>
</template>

<style scoped>
@font-face {
    font-family: iconfont;
    src: url(@/assets/fonts/iconfont-fce1c90e.woff2) format("woff2"),
    url(@/assets/fonts/iconfont-951038f5.woff) format("woff"),
    url(@/assets/fonts/iconfont-09956ca3.ttf) format("truetype")
}

.iconfont {
    font-family: iconfont !important;
    font-size: 16px;
    font-style: normal;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    margin: 0 10px;
}

.icon-playedu:before {
    content: ""
}

.icon-xuexi:before {
    content: ""
}

.icon-wode:before {
    content: ""
}

.icon-shouye:before {
    content: ""
}

.icon-xiala:before {
    content: ""
}

.icon-close:before {
    content: ""
}

.icon-fullscreen:before {
    content: ""
}

.icon-speed:before {
    content: ""
}

.icon-mute:before {
    content: ""
}

.icon-play:before {
    content: ""
}

.icon-pause:before {
    content: ""
}

.icon-unmute:before {
    content: ""
}

.icon-tips:before {
    content: ""
}

.icon-fold:before {
    content: ""
}

.icon-12:before {
    content: ""
}

.icon-waterprint:before {
    content: ""
}

.icon-adduser:before {
    content: ""
}

.icon-upvideo:before {
    content: ""
}

.icon-onlinelesson:before {
    content: ""
}

.icon-department:before {
    content: ""
}

.icon-drag:before {
    content: ""
}

.icon-edit:before {
    content: ""
}

.icon-delete:before {
    content: ""
}

.icon-video:before {
    content: ""
}

.icon-home:before {
    content: ""
}

.icon-category:before {
    content: ""
}

.icon-file:before {
    content: ""
}

.icon-study:before {
    content: ""
}

.icon-user:before {
    content: ""
}

.icon-setting:before {
    content: ""
}

.icon-password:before {
    content: ""
}

.icon-a-logout:before {
    content: ""
}

</style>