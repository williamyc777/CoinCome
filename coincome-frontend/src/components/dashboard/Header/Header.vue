<template>
<header class="header">
    <div class="logo">BitCome</div>
    <nav class="nav">
    <a>Dashboard</a>
    <a>Upload CSV</a>
    <a>My Portfolio</a>
    <a>Recommendations</a>
    <a>Market</a>
    </nav>
    <div class="auth">
    <el-button class="btn-signin" @click="$router.push('/signin')">
    Sign in
    </el-button>

    <el-button class="btn-signup" @click="$router.push('/signup')">
    Sign up
    </el-button>
    </div>
</header>
</template>

<script setup>

</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 40px;
  border-bottom: 1px solid #eee;
}

.logo {
  font-size: 22px;
  font-weight: bold;
  color: #3f7df3;
}

.nav a {
  margin: 0 15px;
  cursor: pointer;
}

.auth {
  display: flex;
  gap: 10px;
}
</style>