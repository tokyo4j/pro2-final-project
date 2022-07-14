<template>
  <div class="container">
    <p class="fs-5 m-4 mt-5" style="font-family: Georgia, serif;">If you need furniture, you can choose from the following to rent!</p>
    <div class="card mb-5">
      <div class="card-header h5 mb-4">Furniture List</div>
      <table class="table table-striped table-hover">
        <thead>
          <th>ID</th>
          <th>Name</th>
          <th>Amount</th>
          <th>Available</th>
          <th>Image</th>
          <th>Select Amount & Lease</th>
        </thead>
        <tbody>
          <tr v-for="furn in computedFurns" :key="furn.id">
            <td>{{ furn.id }}</td>
            <td>{{ furn.name }}</td>
            <td>{{ furn.amount }}</td>
            <td>{{ furn.amount - furn.leasedAmount }}</td>
            <td><img width="100" height="100" v-bind:src="furn.imgUrl" /></td>
            <td>
              <div class="input-group mt-4">
                <input type="number" min="1" :max=furn.amount-furn.leasedAmount
                class="form-control" placeholder="Amount"
                v-model="furn.inputAmount" />
                <button class="btn btn-primary" type="button"
                @click="handleLease(furn.id, furn.inputAmount)">Lease</button>
              </div>
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
      return this.furns.map(furn => ({...furn, inputAmount: ""}))
    }
  }
};

</script>
