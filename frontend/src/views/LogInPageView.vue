<template>
    <div class="container">
        <h1>网上点餐系统</h1>
        <div class="box">
            <div class="left">
                <img src="http://localhost/images/login.jpg" alt="Background Image" />
            </div>
            <div class="right">
                <h1>登录</h1>
                <input type="text" class="inputItem" v-model="username" placeholder="用户名" required />
                <input type="password" class="inputItem" v-model="password" placeholder="密码" required />
                <a href="#" class="forgetPassword">忘记密码?</a>
                <button class="btn" @click.prevent="login">登录</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            username: '',
            password: ''
        };
    },
    methods: {
        login() {
            if (this.username === 'xu' && this.password === '2411') {
                this.$router.push({ name: 'home' });
            } else {
                alert('用户名或密码错误');
            }
        }
    }
};
</script>

<style scoped>
body {
    background: linear-gradient(to right, #65CBF7, #B3A5FC);
    width: 100vw;
    height: 100vh;
    margin: 0;
    display: flex;
    align-items: center;
    justify-content: center;
}

.box {
    width: 60%;
    height: 450px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.8);
    display: flex;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    justify-content: space-between;
}

.left {
    flex: 1;
    height: 100%;
}

.left>img {
    display: block;
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.right {
    width: 35%;
    height: 100%;
    background-color: #fff;
    box-sizing: border-box;
    padding: 0 20px;
}

h1 {
    text-align: center;
    padding-top: 45px;
    margin-top: 0;
}

.inputItem {
    height: 44px;
    padding: 0;
    padding-left: 5px;
    border: none;
    background-color: transparent;
    outline: none;
    border-bottom: 3px solid #B3A5FC;
    width: 100%;
    font-size: 18px;
    margin-top: 20px;
}

.forgetPassword {
    margin-top: 25px;
    color: #9c3493;
    text-align: right;
    display: block;
}

.btn {
    background: linear-gradient(to right, #65CBF7, #B3A5FC);
    color: #9c3493;
    font-weight: bold;
    border: none;
    border-radius: 30px;
    height: 46px;
    width: 80%;
    font-size: 18px;
    display: block;
    margin: auto;
    margin-top: 30px;
}

@media screen and (max-width: 960px) {
    .box {
        display: block;
        height: auto;
    }

    .left,
    .right {
        width: 100%;
        margin-top: 0;
    }

    .left {
        height: 200px;
    }

    .right {
        padding: 2vw 2vw;
    }

    h1 {
        padding-top: 0;
        margin-bottom: 1vw;
    }

    .inputItem,
    .forgetPassword,
    .btn {
        margin-top: 2vw;
    }
}

@media screen and (max-width: 750px) {
    .box {
        width: 85%;
    }
}
</style>