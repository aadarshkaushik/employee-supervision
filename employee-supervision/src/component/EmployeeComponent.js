import React, { Component } from 'react'
import EmployeeService from './EmployeeService';

export default class EmployeeComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            employee:{},
            department:{},
            organization:{}
        }
    }
    componentDidMount(){
        EmployeeService.getEmployee().then((response)=>{
            this.setState({employee : response.data.employee})
            this.setState({department : response.data.department})
            this.setState({organization : response.data.organization})

            console.log(this.state.employee);
            console.log(this.state.department);
            console.log(this.state.organization);
        })
    }

  render() {
    return (
      <div>
        <br/>
        <br/>
        <div className='card offset-md-3 col-md-6'>
            <h3 className='text-center card-header'>View Employee Details</h3>
            <div className='card-body'>
                <div className='row'>
                    <p><strong>Employee First Name: </strong>{this.state.employee.firstName}</p>
                </div>
                <div className='row'>
                    <p><strong>Employee Last Name: </strong>{this.state.employee.lastName}</p>
                </div>
                <div className='row'>
                    <p><strong>Employee Email: </strong>{this.state.employee.email}</p>
                </div>
            </div>
            <h3 className='text-center card-header'>View Department Details</h3>
            <div className='card-body'>
                <div className='row'>
                    <p><strong>Department Name: </strong>{this.state.department.departmentName}</p>
                </div>
                <div className='row'>
                <p><strong>Department Description: </strong>{this.state.department.departmentDescription}</p>
                </div>
                <div className='row'>
                <p><strong>Department Code: </strong>{this.state.department.departmentCode}</p>
                </div>
            </div>
            <h3 className='text-center card-header'>View Organization Details</h3>
            <div className='card-body'>
                <div className='row'>
                    <p><strong>Organization Name: </strong>{this.state.organization.name}</p>
                </div>
                <div className='row'>
                <p><strong>Organization Description: </strong>{this.state.organization.description}</p>
                </div>
                <div className='row'>
                <p><strong>Organization Code: </strong>{this.state.organization.code}</p>
                </div>
            </div>
        </div>
        <br/>
            <br/>
      </div>
    )
  }
}