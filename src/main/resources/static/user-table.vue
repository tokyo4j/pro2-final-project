<template>
  <div class="container">
    <p class="fs-5 m-4 mt-5" style="font-family: Georgia, serif;">We can add and delete user information from this form.
    </p>
    <div class="card mb-5">
      <div class="card-header h5">User Management</div>
      <div class="card-body">
        <form class="row">
          <div class="col">
            <input class="form-control" v-model="addUser_name" placeholder="User Name" />
          </div>
          <div class="col">
            <input class="form-control" v-model="addUser_password" placeholder="Password" />
          </div>
          <div class="col">
            <button type="button" class="btn btn-outline-primary" @click="handleAddUser">Add User</button>
          </div>
        </form>
        <hr class="mb-4">

        <table class="table table-striped table-hover">
          <thead>
            <th>ID</th>
            <th>Name</th>
            <th>Password</th>
            <th>Delete</th>
            <th>Edit</th>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.password }}</td>
              <td>
                <button type="button" class="btn btn-outline-danger" width="100" height="100"
                  @click="handleDelUser(user.id)">Del</button>
              </td>
              <td>
                <button type="button" class=" btn btn-success" @click="handleEditButton(user)">Edit</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!-- Modal -->
    <div v-if="isModalOpen">
      <div class="modal" @click.self="isModalOpen = false" style="display: block;">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Edit this user</h4>
              <button @click="isModalOpen = false" type="button" class="btn-close" data-bs-dismiss="modal"
                aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form>
                <div class="mb-3">
                  <label for="recipient-name" class="col-form-label">New name:</label>
                  <input v-model="editingUser.name" type="text" class="form-control" />
                </div>
                <div class="mb-3">
                  <label for="recipient-name" class="col-form-label">New password:</label>
                  <input v-model="editingUser.password" type="text" class="form-control" />
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button @click="isModalOpen = false" type="button" class="btn btn-secondary"
                data-bs-dismiss="modal">Close</button>
              <button @click="handleEditSubmit" type="button" class="btn btn-primary">Submit</button>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-backdrop show"></div>
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
      editingUser: null,
      isModalOpen: false
    };
  },
  methods: {
    async handleAddUser() {
      await fetch(
        `/api/user?user_name=${this.addUser_name}&password=${this.addUser_password}`,
        { method: "PUT" }
      );
      this.$emit("users-update");
      this.$emit("notify", { message: "User added!", status: "success" });
    },

    async handleDelUser(userId) {
      const res = await fetch(`/api/user/${userId}`, { method: "DELETE" });
      if (res.ok) {
        this.$emit("users-update");
        this.$emit("notify", "User deleted!");
      } else {
        const json = await res.json();
        this.$emit("notify", `ERROR: ${json.error}`, "alert");
      }
    },

    handleEditButton(user) {
      this.editingUser = JSON.parse(JSON.stringify(user))
      this.isModalOpen = true;
    },

    async handleEditSubmit() {
      await fetch(`/api/user/${this.editingUser.id}?name=${this.editingUser.name}&password=${this.editingUser.password}`, {
        method: "PATCH",
      });
      this.isModalOpen = false;
      this.$emit("users-update");
      this.$emit("notify", { message: "User updated!", status: "success" });
    }
  },
};
</script>
