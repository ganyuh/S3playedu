import {createRouter, createWebHashHistory} from "vue-router";
import useUserStore from "@/stores/modules/backstage/adminUser/user.js";
import {checkToken} from "@/utils/Base64Utils.js";

const routes = [
    {
        path: "",
        redirect: "/b/"
    },
    {
        path: "/b",
        name: "Backstage",
        children: [
            {
                path: "",
                name: "logout",
                component: () => import("../views/backstage/home/Layout.vue"),
                children: [
                    {
                        path: "",
                        name: "home",
                        meta: {
                            title: "后台首页"
                        },
                        component: () => import("@views/backstage/home/Home.vue"),
                    },
                    {
                        path: "resource-category",
                        name: "category",
                        meta: {
                            title: "资源分类"
                        },
                        component: () => import("@views/backstage/categories/index.vue"),

                    },
                    {
                        path: "resource-manage",
                        name: "resource",
                        reject: "resource-manage/video",
                        children: [
                            {
                                path: "video",
                                name: "video",
                                meta: {
                                    title: "视频管理"
                                },
                                component: () => import("@views/backstage/resource/VideoManager.vue")
                            }
                        ]
                    },
                    {
                        path: "student",
                        name: "student",
                        meta: {
                            title: "学员管理"
                        },
                        component: () => import("@views/backstage/student/student/StudentManage.vue"),
                    },
                    {
                        path: "dept",
                        name: "dept",
                        meta: {
                            title: "部门管理"
                        },
                        component: () => import("@views/backstage/student/dept/DeptManage.vue"),
                    },
                    {
                        path: "classes",
                        name: "classes",
                        meta: {
                            title: "线上课"
                        },
                        component: () => import("@views/backstage/course/classes/Classes.vue"),
                    }
                ]
            },
            {
                path: "login",
                name: "login",
                meta: {
                    title: "管理员登录"
                },
                component: () => import("../views/backstage/login/LogIn.vue"),
            }
        ]
    },
    {
        path: "/fd",
        name: "FrontDesk",
        children: [
            {
                path:"",
                name:"home01",
                component:()=>import("@views/frontdesk/home/index.vue")
            },
            {
                path: "classes",
                name: "classes",
                component:()=>import("@views/frontdesk/home/classes/classes.vue")
            },
            {
                path: "classes2",
                name: "classes2",
                component:()=>import("@views/frontdesk/home/classes/classes2.vue")
            },
            {
                path: "classes3",
                name: "classes3",
                component:()=>import("@views/frontdesk/home/classes/classes3.vue")
            },
            {
                path: "classes4",
                name: "classes4",
                component:()=>import("@views/frontdesk/home/classes/classes4.vue")
            }
        ]
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

/**
 * 路由守卫
 */
// router.beforeEach((to, from) => {
//     let goto = true;
//     const userStore = useUserStore();
//     let tokenInfo = checkToken(userStore.token);
//     // console.log("to: ", to)
//     // console.log("from: ", from)
//     // console.log("tokenInfo?.expired: ", tokenInfo?.expired)
//     const expired = !tokenInfo || tokenInfo.expired;
//     if (to.fullPath !== "/b/login" && expired) {
//         // console.log("GOTO Login Page")
//         userStore.token = "";
//         goto = "/b/login";
//     } else if (to.fullPath === "/b/login" && !expired) {
//         goto = "/b";
//     }
//     if (to.meta.title) {
//         document.title = to.meta.title
//     }
//     return goto;
// })

export default router;
