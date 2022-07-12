<template>
  <div>
    <div>
      <table>
        <thead>
          <th>ID</th>
          <th>Name</th>
          <th>Amount</th>
          <th>Available</th>
          <th>Image</th>
          <th></th>
        </thead>
        <tbody>
          <tr v-for="furn in furns">
            <td>{{ furn.id }}</td>
            <td>{{ furn.name }}</td>
            <td>{{ furn.amount }}</td>
            <td>{{ furn.amount - furn.leasedAmount }}</td>
            <td><img width="100" height="100" v-bind:src="furn.imgUrl" /></td>
            <td><button @click="handleLease(furn.id)">Lease</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
module.exports = {
  props: ["furns"],
  data() {
    return {};
  },
  methods: {
    async handleLease(furnId) {
      await fetch(`/api/lease?furniture_id=${furnId}&amount=${3}`, {
        method: "PUT",
      });
      this.$emit("furn-leased");
    },
  },
};
</script>
