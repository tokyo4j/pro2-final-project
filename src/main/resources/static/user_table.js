export default {
  template: `
<div>
  <div>
    <label>User Name</label>
    <input v-model="addUser_name" />
    <label>Password</label>
    <input v-model="addUser_password" />
    <button @click="handleAddUser">Add User</button>
  </div>
  <div>
    <table>
      <thead>
        <th>ID</th>
        <th>Name</th>
        <th>Password</th>
        <th></th>
      </thead>
      <tbody>
        <tr v-for="user in users">
          <td>{{user.id}}</td>
          <td>{{user.name}}</td>
          <td>{{user.password}}</td>
          <td><button width="100" height="100" @click="handleDelUser(user.id)">Del</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
`,
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
