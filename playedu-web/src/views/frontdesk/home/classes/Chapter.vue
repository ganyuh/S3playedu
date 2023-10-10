<script setup>

    const props = defineProps({
        chapterTitle: {
            type: String,
            default: "第一章"
        },
        videoList: {
            type: Array,
            default: () => []
        }
    })
</script>

<template>
    <div class="chapter">
        <div>{{ props.chapterTitle }}</div>
        <div v-for="(item,index) in props.videoList" :key="index">
            <div>
                {{ item.name }}
            </div>
            <div>
                <router-link :to="item.url">开始学习</router-link>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>