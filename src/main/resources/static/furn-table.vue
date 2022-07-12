<template>
  <div>
    <div>
      <label>Furniture Name</label>
      <input v-model="addFurn_name" />
      <label>Amount</label>
      <input v-model.number="addFurn_amount" />
      <input type="file" ref="addFurn_imgFileInput" />
      <button @click="handleAddFurn">Add Furniture</button>
    </div>
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
            <td><button @click="handleDelFurn(furn.id)">Del</button></td>
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
      addFurn_name: null,
      addFurn_amount: null,
    };
  },
  methods: {
    async handleAddFurn() {
      const formData = new FormData();
      formData.append("img_file", this.$refs.addFurn_imgFileInput.files[0]);
      await fetch(
        `/api/furniture?name=${this.addFurn_name}&amount=${this.addFurn_amount}`,
        {
          method: "PUT",
          body: formData,
        }
      );
      this.$emit("furns-update");
    },

    async handleDelFurn(furnId) {
      await fetch(`/api/furniture/${furnId}`, { method: "DELETE" });
      this.$emit("furns-update");
    },
  },
};
</script>
