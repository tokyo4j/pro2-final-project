<template>
  <div class="container">
    <p class="fs-5 m-4 mt-5" style="font-family: Georgia, serif;">When you have completed the return, press the button.</p>
    <div class="card mb-5">
      <div class="card-header h5 mb-4">Lease List</div>
      <table class="table table-striped table-hover">
        <thead>
          <th>Furniture ID</th>
          <th>Furniture</th>
          <th>Amount</th>
          <th>Image</th>
          <th>Return Button</th>
        </thead>
        <tbody>
          <tr v-for="lease in leases" :key="lease.furn.id">
            <td>{{ lease.furn.id }}</td>
            <td>{{ lease.furn.name }}</td>
            <td>{{ lease.amount }}</td>
            <td>
              <img width="100" height="100" v-bind:src="lease.furn.imgUrl" />
            </td>
            <td><button type="button" class="m-4 btn btn-success"
                @click="handleReturn(lease.id)">Return</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
module.exports = {
  props: ["leases"],
  data() {
    return {
      addLease_userId: "",
      addLease_furnId: "",
      addLease_amount: 0,
    };
  },
  methods: {
    async handleReturn(leaseId) {
      await fetch(`/api/lease/${leaseId}`, { method: "DELETE" });
      this.$emit("furn-returned");
    },
  },
};
</script>
