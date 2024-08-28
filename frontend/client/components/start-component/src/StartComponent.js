import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './startComponent.css';
import '@eui/layout'
import '@eui/table';

@definition('e-start-component', {
  style,
  home: 'start-component',
  props: {
    parentCallback: { type: Function },
  },
})
export default class StartComponent extends LitComponent {

  constructor(){
    super();
    var username = localStorage.getItem('username');
    this.getUser(username);
  }

  async getUser(username){
    var createUser =  () => this.createUser(username);
    fetch('http://localhost:9090/db/getUserByEmail/'+username)
    .then(function(response) {
      return response.json();
    }).then(function(data) {
        this.user = {
          id: data.id,
          email: data.email,
          submissions: data.userSubmissions
        }
        for(var i = 0; i < this.user.submissions.length; i++){
          this.rows[i] = {col1: (new Date(this.user.submissions[i].date)).toUTCString(), hiddenCol: this.user.submissions[i].answers}
        }
      this.executeRender();
    }.bind(this)).catch(function(ex) {
      console.log('User not found!', ex);
      createUser();
    });
  }

  async createUser(username){
    fetch("http://localhost:9090/db/createUser/", {
      method: "POST",
      headers: {'Content-Type': 'application/json'}, 
      body: JSON.stringify({ "email": username })
    }).then(() => {
      this.getUser(username);
    });
  }

  panelShown = false;
  dialogShown = false;
  user = {
    id: "",
    email: "",
    submissions: []
  }
  graphAnswers = {
    "Culture": 0,
    "Production/Service Design": 0,
    "Team": 0,
    "Process": 0,
    "Architecture": 0,
    "Maintanence and Operations": 0,
    "Delivery": 0,
    "Provisioning": 0,
    "Infrastructure": 0
    };
  graphTimeStamp = "";
  columns = [
    { title: 'My Graphs', attribute: 'col1' }
  ];
  rows = [];

  showPanel(){
    this.panelShown = true;
    this.executeRender();
  }

  hidePanel(){
    this.panelShown = false;
    this.executeRender();
  }

  selectGraph(row){
    this.panelShown = false;
    this.graphAnswers = row.detail.hiddenCol;
    this.graphTimeStamp = row.detail.col1;
    this.dialogShown = true;
    this.executeRender();
  }
  

  render() {
    return html`
    <div class="information-area">
    <h1 class="">Cloud Native Maturity Matrix</h1>
    <h2>Take this questionaire to assess where your organisation is in their Cloud Native journey.</h2>
    <eui-base-v0-dialog @eui-dialog:cancel=${() => this.dialogShown = false} .show="${ this.dialogShown }" fullscreen>
      <div slot="content">
      <e-graph-component 
      style="margin-top: -30px;"
      .answers=${this.graphAnswers}
      .timestamp=${this.graphTimeStamp}
      ></e-graph-component>
      </div>
    </eui-base-v0-dialog>
    <eui-layout-v0-flyout-panel .show="${ this.panelShown }">
    <div slot="content">
      <eui-table-v0 @eui-table:row-click=${(data) => this.selectGraph(data)} .columns=${this.columns} .data=${this.rows}></eui-table-v0>
    </div>
    <eui-base-v0-button @click="${() => this.hidePanel() }" icon="cross" primary slot="footer">Close</eui-base-v0-button></eui-layout-v0-flyout-panel>
    <table class="btn-layout">
      <tr>
        <td>
        <eui-base-v0-button icon="cloud"
        @click="${() => this.parentCallback(true)}"
        primary
        >Take Assessment</eui-base-v0-button>
        </td>
        <td>
        <eui-base-v0-button icon="graph-plot"
        @click="${() => this.showPanel() }"
        primary
        >View Graphs</eui-base-v0-button>
        </td>
      </tr>
    </table>
    <h4>Signed in as: ${this.user.email}</h4>
    </div>`;
  }
}

StartComponent.register();
