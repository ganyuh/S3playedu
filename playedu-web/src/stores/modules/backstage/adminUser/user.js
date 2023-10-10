import {defineStore} from "pinia";

const useUserStore = defineStore("playedu-user", {
    state: () => ({
        token: "",
        userInfo: {},
    }),
    actions: {
        setToken(token) {
            this.token = token;
        },
        setUserInfo(userInfo) {
            this.userInfo = userInfo;
        },
    },
    persist: {
        enabled: true,
        storage: localStorage,
        key: 'playedu-user',
        paths: void (0),
    },
});
export default useUserStore;
