import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './graphComponent.css';
import '@eui/table';

@definition('e-graph-component', {
  style,
  home: 'graph-component',
  props: {
    answers: { attribute: true, type: Object },
    timestamp: { attribute: true, type: String }
  },
})
export default class GraphComponent extends LitComponent {

  async getCategories(){
    fetch('http://localhost:9090/db/getCategories/')
    .then(function(response) {
      return response.json();
    }).then(function(data) {
      this.columns[0] = {title:"Stage", attribute:"col0"}
      for(var i = 0; i < Object.keys(data[0].values).length; i++){
        this.columns[i+1] = {title: Object.keys(data[0].values)[i], attribute: "col"+(i+1)};
        for(var j =0; j < data.length; j++){
          var str = "col"+(i+1);
          var obj = Object.values(data[j].values)[i];
          this.rows[j] = {...this.rows[j], col0: data[j].name, [str]: obj}
        }
      }
      this.executeRender();
    }.bind(this)).catch(function(ex) {
      console.log('An Error occurred parsing JSON!', ex);
    })
  }

  constructor(){
    super();
    this.getCategories();
  }

  generateGraph(){
    var graphVals = [];
    var i = 0;
    var average = 0;
    Object.values(this.answers).forEach(value => {
      average += value;
      var valStruct = [(value/4)*100, (value/4)*100]
      graphVals[i] = valStruct;
      i++;
    });
    average = average / 9;
   
    if(average >= 3.5){
      this.result = "Cloud Native";
    }
    else if(average >= 2.5){
      this.result = "Agile";
    }
    else if(average >= 1.5){
      this.result = "Waterfall";
    }
    else{
      this.process = "No Process"
    }
    return graphVals;

  }

  categories = [];
  columns = [];
  rows = [];
  result = "";



  render() {
    var cols = this.columns.map(function(col, i){
      return html`
        <th class="table-head">
          ${col.title}
        </th>
      `;
    });
    var vals = this.generateGraph();
    var rows = this.rows.map(function(row, i){
      return html`
        <tr style="background-size: 610px; background-position: 150px; background-image: linear-gradient(90deg, #4D97ED ${vals[i][0]}%,  #E9F0F9 ${vals[i][1]}%);">
          <td class="table-head">
            ${row.col0}
          </td>
          <td>
            ${row.col1}
          </td>
          <td>
            ${row.col2}
          </td>
          <td>
            ${row.col3}
          </td>
          <td>
            ${row.col4}
          </td>
          <td class="table-head">
            ${row.col5}
          </td>
        </tr>
      `;
    })
    return html`
    <div class="information-area">
    <h2>Submitted: ${this.timestamp} &emsp;Result: <b>${this.result}</b></h2>
      <div class="table-area">
        <table class="graph-table">
          <tr>
            ${cols}
          </tr>
          ${rows}
        </table>
      </div>
    </div>`;
  }
}

GraphComponent.register();
