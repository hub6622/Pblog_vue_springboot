import axios from "axios";
import { ElMessage } from "element-plus";
import {useTokenStore} from "@/stores/token.js"
import {useUserInfoStore} from "@/stores/userInfo.js"
import router from "@/router/router.js";

const baseURL = '/api';
const instance = axios.create({baseURL})

instance.interceptors.response.use(
    result=>{
        if(result.code===200){
            return result.data;
        }
        return result.data;
    },
    err=>{
        if (err.response.status===401){
            // 由于全局调用的话，pinia实例还没挂载，所以不能全局调用。
            const TokenStore = useTokenStore();
            const UserInfoStore = useUserInfoStore();
            TokenStore.removeToken();
            UserInfoStore.removeInfo();
            ElMessage.error('登录失效，请重新登录')
            router.push('/login');
        }else {
            ElMessage.error('服务异常')
        }
        return Promise.reject(err);
    }
)
instance.interceptors.request.use(
    config=>{
        // 由于全局调用的话，pinia实例还没挂载，所以不能全局调用。
        let TokenStore = useTokenStore();
        if (TokenStore.token){
            config.headers.Authorization = TokenStore.token;
        }
        return config;
    },
    err=>{
        ElMessage.error('服务异常')
        return Promise.reject(err);
    }
)
export default instance;