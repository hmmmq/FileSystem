<script setup>
import Simplebar from 'simplebar-vue';
import 'simplebar-vue/dist/simplebar.min.css';
</script>

<template>
    <div id="app2">
        <Simplebar>
            <div v-for="n in 10" :key="n">Example content</div>
        </Simplebar>
    </div>
</template>

<style>
#app2 {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}
</style>