<template>
  <div class="container">
        <p class="fs-5 m-4 mt-5" style="font-family: Georgia, serif;">We can add and delete user information from this form.</p>
    <div class="card mb-5">
      <div class="card-header h5">User Management</div>
      <div class="card-body">
        <form class="row">
        <div class="col">
        <!-- <label>User Name</label> -->
        <input class="form-control"
        v-model="addUser_name" placeholder="User Name" />
        </div>
        <div class="col">
        <!-- <label>Password</label> -->
        <input class="form-control"
        v-model="addUser_password" placeholder="Password" />
        </div>
        <div class="col">
        <button type="button" class="btn btn-outline-primary" @click="handleAddUser">Add User</button>
        </div>
        </form>
        <hr class="mb-4">

        <table class="table table-striped table-hover">>
          <thead>
            <th>ID</th>
            <th>Name</th>
            <th>Password</th>
            <th>Delete</th>
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
