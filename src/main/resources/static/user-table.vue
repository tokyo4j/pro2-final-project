<template>
  <div class="container">
    <div class="card mb-5">
      <div class="card-header h5">User Management</div>
      <div class="card-body">
        <label>User Name</label>
        <input v-model="addUser_name" />
        <label>Password</label>
        <input v-model="addUser_password" />
        <button type="button" class="btn btn-outline-primary" @click="handleAddUser">Add User</button>
        <hr class="mb-5">

        <table class="table table-striped table-hover">>
          <thead>
            <th>ID</th>
            <th>Name</th>
            <th>Password</th>
            <th></th>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.password }}</td>
              <td><button type="button" class="btn btn-outline-danger" width="100" height="100"
                  @click="handleDelUser(user.id)">Del</button></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
module.exports = {
  props: ["users"],
  data() {
    return {
      addUser_name: "",
      addUser_password: "",
    };
  },
  methods: {
    async handleAddUser() {
      await fetch(
        `/api/user?user_name=${this.addUser_name}&password=${this.addUser_password}`,
        { method: "PUT" }
      );
      this.$emit("users-update");
    },

    async handleDelUser(userId) {
      await fetch(`/api/user/${userId}`, { method: "DELETE" });
      this.$emit("users-update");
    },
  },
};
</script>
