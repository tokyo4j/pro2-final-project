<template>
    <transition>
        <div v-if="active" ref="notification" class="notification-wrapper alert alert-success" :class="{
            'alert-success': data.status === 'success',
            'alert-danger': data.status === 'alert',
        }">
            {{ data.message }}
        </div>
    </transition>
</template>
<script>
module.exports = {
    props: ["data"],
    data() {
        return {
            active: false,
            timeoutId: null,
        }
    },
    watch: {
        data() {
            this.active = true;
            clearTimeout(this.timeoutId);
            this.timeoutId = setTimeout(() => {
                this.active = false;
            }, 2000);
        }
    }
}
</script>
<style>
.notification-wrapper {
    position: fixed;
    top: 10px;
    left: 50%;
    transform: translateX(-50%);
}

.v-enter-active,
.v-leave-active {
    transition: opacity 0.5s ease;
}

.v-enter-from,
.v-leave-to {
    opacity: 0;
}
</style>