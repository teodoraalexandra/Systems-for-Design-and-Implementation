import { Component, OnInit } from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Location} from "@angular/common";
import swal from "sweetalert";

@Component({
  selector: 'app-client-new',
  templateUrl: './client-new.component.html',
  styleUrls: ['./client-new.component.css']
})
export class ClientNewComponent implements OnInit {

  constructor(private clientService: ClientService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveClient(serialNumber: string, name: string)  {
    console.log("Saving client", serialNumber, name);

    if (serialNumber == null || name == null ||
      serialNumber == "" || name == "") {
      swal("Please enter values in all fields.");
    } else {
      this.clientService.saveClient({
        id: 0,
        serialNumber,
        name
      })
        .subscribe(client => console.log("saved client: ", client));

      this.location.back();
    }
  }
}
