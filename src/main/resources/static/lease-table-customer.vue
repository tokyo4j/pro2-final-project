<template>
  <div>
    <div>
      <table>
        <thead>
          <th>Furniture ID</th>
          <th>Furniture</th>
          <th>Amount</th>
          <th></th>
        </thead>
        <tbody>
          <tr v-for="lease in leases">
            <td>{{ lease.furn.id }}</td>
            <td>{{ lease.furn.name }}</td>
            <td>{{ lease.amount }}</td>
            <td>
              <img width="100" height="100" v-bind:src="lease.furn.imgUrl" />
            </td>
            <td><button @click="handleReturn(lease.id)">Return</button></td>
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
