<template>
  <div class="container">
        <p class="fs-5 m-4 mt-5" style="font-family: Georgia, serif;">We can add and delete your customer's rental furniture from this form.</p>
    <div class="card mb-5">
      <div class="card-header h5">Lease Management</div>
      <div class="card-body">
        <form class="row">
          <div class="col">
          <select class="form-select" v-model="addLease_userId">
            <option value="" disabled selected>User</option>
            <option v-for="user in users" v-bind:value="user.id" :key="user.id">
              {{ user.name }}
            </option>
          </select>
          </div>
          <div class="col">
          <select class="form-select" v-model="addLease_furnId">
            <option value="" disabled selected>Furniture</option>
            <option v-for="furn in furns" v-bind:value="furn.id" :key="furn.id">
              {{ furn.name }}
            </option>
          </select>
          </div>
          <div class="col">
          <input type="number" min="1" class="form-control"
          v-model.number="addLease_amount" placeholder="Amount" />
          </div>
          <div class="col">
          <button type="button" class="btn btn-outline-primary" @click="handleAddLease">Add Lease</button>
          </div>
        </form>
        <hr class="mb-5">

        <table class="table table-striped table-hover">
          <thead>
            <th>ID</th>
            <th>User ID</th>
            <th>User</th>
            <th>Furniture ID</th>
            <th>Furniture</th>
            <th>Amount</th>
            <th>Image</th>
            <th>Delete</th>
          </thead>
          <tbody>
            <tr v-for="lease in leases" :key="lease.id">
              <td>{{ lease.id }}</td>
              <td>{{ lease.user.id }}</td>
              <td>{{ lease.user.name }}</td>
              <td>{{ lease.furn.id }}</td>
              <td>{{ lease.furn.name }}</td>
              <td>{{ lease.amount }}</td>
              <td><img width="100" height="100" v-bind:src="lease.furn.imgUrl"></td>
              <td><button type="button" class="mt-4 btn btn-outline-danger"
                  @click="handleDelLease(lease.id)">Del</button></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
module.exports = {
  props: ["leases", "users", "furns"],
  data() {
    return {
      addLease_userId: "",
      addLease_furnId: "",
      addLease_amount: "",
    };
  },
  methods: {
    async handleAddLease() {
      await fetch(
        `/api/lease?user_id=${this.addLease_userId}&furniture_id=${this.addLease_furnId}&amount=${this.addLease_amount}`,
        { method: "PUT" }
      );
      this.$emit("leases-update");
    },

    async handleDelLease(leaseId) {
      await fetch(`/api/lease/${leaseId}`, { method: "DELETE" });
      this.$emit("leases-update");
    },
  },
};
</script>
