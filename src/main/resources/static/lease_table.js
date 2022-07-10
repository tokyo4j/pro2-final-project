export default {
  template: `
<div>
  <div>
    <label>User ID</label>
    <input v-model="addLease_userId" />
    <label>Furniture ID</label>
    <input v-model="addLease_furnId" />
    <label>Amount</label>
    <input v-model.number="addLease_amount" />
    <button @click="handleAddLease">Add Lease</button>
  </div>
  <div>
    <table>
      <thead>
        <th>ID</th>
        <th>User ID</th>
        <th>User</th>
        <th>Furniture ID</th>
        <th>Furniture</th>
        <th>Amount</th>
        <th></th>
      </thead>
      <tbody>
        <tr v-for="lease in leases">
          <td>{{lease.id}}</td>
          <td>{{lease.user.id}}</td>
          <td>{{lease.user.name}}</td>
          <td>{{lease.furn.id}}</td>
          <td>{{lease.furn.name}}</td>
          <td>{{lease.amount}}</td>
          <td><img width="100" height="100" v-bind:src="lease.furn.imgUrl"></td>
          <td><button @click="handleDelLease(lease.id)">Del</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
`,
  props: ["leases"],
  data() {
    return {
      addLease_userId: "",
      addLease_furnId: "",
      addLease_amount: 0,
      // users: [],
    };
  },
  methods: {
    async handleAddLease() {
      await fetch(
        `${API_DOMAIN}/lease?user_id=${this.addLease_userId}&furniture_id=${this.addLease_furnId}&amount=${this.addLease_amount}`,
        { method: "PUT" }
      );
      this.$emit("leases-update");
      this.$emit("furns-update");
    },

    async handleDelLease(leaseId) {
      await fetch(`${API_DOMAIN}/lease/${leaseId}`, { method: "DELETE" });
      this.$emit("leases-update");
      this.$emit("furns-update");
    },
  },
};
