import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ResourceDetails } from '../data.model';
import { ProjectService } from '../services/project.service';
import { FlexFieldsService } from '../flex-fields.service';
import { FlexFields } from '../flex-fields.model';

@Component({
  selector: 'app-resource-dialog',
  templateUrl: './resource-dialog.component.html',
  styleUrls: ['./resource-dialog.component.css']
})
export class ResourceDialogComponent implements OnInit {
  resourceForm: FormGroup;
  isNewResource: boolean;
  editedResource: ResourceDetails;
  customers: string[] = [];
  streams: string[] = [];
  skillSets: string[] = [];
  flexValues: string[] = [];

  constructor(
    public dialogRef: MatDialogRef<ResourceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ResourceDetails,
    private fb: FormBuilder,
    private projectService: ProjectService,
    private flexFieldsService: FlexFieldsService
  ) {
    this.isNewResource = !this.data.customId;
    this.editedResource = { ...this.data };

    this.resourceForm = this.createResourceForm(this.editedResource);
  }

  ngOnInit(): void {
    this.loadCustomers();

    this.resourceForm.get('customerName')?.valueChanges.subscribe((value: string) => {
      this.onCustomerSelected(value);
    });

    this.resourceForm.get('skillSet')?.valueChanges.subscribe((value: string) => {
      this.onSkillSetSelected(value);
    });
    this.resourceForm.get('internalExternal')?.valueChanges.subscribe((value: string) => {
      this.updateL1InterviewDateField(value);
    });

    if (!this.isNewResource) {
      this.onCustomerSelected(this.editedResource.customerName);
      this.resourceForm.patchValue(this.editedResource);

      // Fetch flex values for existing skillSet
      this.onSkillSetSelected(this.editedResource.skillSet);
    }
    this.updateL1InterviewDateField(this.resourceForm.get('internalExternal')?.value);
  }

  createResourceForm(resource: ResourceDetails): FormGroup {
    return this.fb.group({
      customId: [resource.customId],
      spid: [resource.spid || '', Validators.required],
      requestedDate: [resource.requestedDate || '', Validators.required],
      customerMgrName: [resource.customerMgrName || '', Validators.required],
      skillSet: [resource.skillSet || '', Validators.required],
      billRate: [resource.billRate || '', Validators.required],
      overallStatus: [resource.overallStatus || ''],
      resourceName: [resource.resourceName || '', Validators.required],
      noOfYears: [resource.noOfYears || '', Validators.required],
      profileSharedDate: [resource.profileSharedDate || ''],
      customerInterviewDate: [resource.customerInterviewDate || ''],
      l1InterviewDate: [{ value: resource.l1InterviewDate || '', disabled: false }],
      dateOfJoin: [resource.dateOfJoin || ''],
      internalExternal: [resource.internalExternal || '', Validators.required],
      flexField1RatingInput: [resource.flexField1Rating || ''],
      flexField1Rating: [''],
      flexField2RatingInput: [resource.flexField2Rating || ''],
      flexField2Rating: [''],
      flexField3RatingInput: [resource.flexField3Rating || ''],
      flexField3Rating: [''],
      flexField4RatingInput: [resource.flexField4Rating || ''],
      flexField4Rating: [''],
      customerName: [resource.customerName || ''],
      stream: [resource.stream || '']
    });
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  onAddSubmit(): void {
    if (this.resourceForm.valid) {
      const formData = this.prepareFormData();
      this.dialogRef.close({ action: 'add', resource: formData });
    } else {
      this.markFormGroupTouched(this.resourceForm);
    }
  }

  onEditSubmit(): void {
    if (this.resourceForm.valid) {
      const formData = this.prepareFormData();
      this.dialogRef.close({ action: 'edit', resource: formData });
    } else {
      this.markFormGroupTouched(this.resourceForm);
    }
  }

  private prepareFormData(): any {
    const formData = {
      ...this.resourceForm.value,
      flexField1Rating: this.resourceForm.get('flexField1Rating')?.value || '',
      flexField2Rating: this.resourceForm.get('flexField2Rating')?.value || '',
      flexField3Rating: this.resourceForm.get('flexField3Rating')?.value || '',
      flexField4Rating: this.resourceForm.get('flexField4Rating')?.value || ''
    };

    // Remove the dropdown input fields from formData
    delete formData.flexField1RatingInput;
    delete formData.flexField2RatingInput;
    delete formData.flexField3RatingInput;
    delete formData.flexField4RatingInput;

    return formData;
  }

  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  loadCustomers(): void {
    this.projectService.getAllCustomers().subscribe(
      (customers: string[]) => {
        this.customers = customers;
      },
      (error) => {
        console.error('Error loading customers:', error);
      }
    );
  }

  onCustomerSelected(customerName: string): void {
    if (customerName) {
      this.projectService.getStreamForCustomer(customerName).subscribe(
        (streams: string[]) => {
          this.streams = streams;
        },
        (error) => {
          console.error('Error loading streams for customer:', error);
        }
      );

      this.flexFieldsService.getSkillSetForCustomer(customerName).subscribe(
        (skillSets: string[]) => {
          this.skillSets = skillSets;
        },
        (error: any) => {
          console.error('Error loading skill sets for customer:', error);
        }
      );
    } else {
      this.streams = [];
      this.skillSets = [];
    }
  }

  onSkillSetSelected(skillSet: string): void {
    if (skillSet) {
      this.flexFieldsService.getFlexFieldsBySkillSet(skillSet).subscribe(
        (flexFields: FlexFields[]) => {
          this.flexValues = flexFields.map(f => f.flexValue);
        },
        (error: any) => {
          console.error('Error loading flex values for skill set:', error);
        }
      );
    } else {
      this.flexValues = [];
    }
  }

  onFlexField1Selected(value: any) {
    this.resourceForm.get('flexField1RatingInput')?.patchValue(value);
  }

  onFlexField2Selected(value: any) {
    this.resourceForm.get('flexField2RatingInput')?.patchValue(value);
  }

  onFlexField3Selected(value: any) {
    this.resourceForm.get('flexField3RatingInput')?.patchValue(value);
  }

  onFlexField4Selected(value: any) {
    this.resourceForm.get('flexField4RatingInput')?.patchValue(value);
  }
  updateL1InterviewDateField(internalExternalValue: string): void {
    const l1InterviewDateControl = this.resourceForm.get('l1InterviewDate');

    if (internalExternalValue === 'internal') {
      l1InterviewDateControl?.disable();
    } else {
      l1InterviewDateControl?.enable();
    }
  }
}
