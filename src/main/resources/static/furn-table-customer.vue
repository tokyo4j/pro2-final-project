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
          <tr v-for="furn in computedFurns">
            <td>{{ furn.id }}</td>
            <td>{{ furn.name }}</td>
            <td>{{ furn.amount }}</td>
            <td>{{ furn.amount - furn.leasedAmount }}</td>
            <td><img width="100" height="100" v-bind:src="furn.imgUrl" /></td>
            <td>
              <input type="number" v-model="furn.inputAmount" />
              <button @click="handleLease(furn.id, furn.inputAmount)">Lease</button>
            </td>
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
    return {
      amounts: []
    };
  },
  methods: {
    async handleLease(furnId, amount) {
      await fetch(`/api/lease?furniture_id=${furnId}&amount=${amount}`, {
        method: "PUT",
      });
      this.$emit("furn-leased");
    },
  },
  computed: {
    computedFurns(){
      return this.furns.map(furn => ({...furn, inputAmount: 0}))
    }
  }
};
</script>
