<template>
    <div style="max-height:80vh;">
        <div class="hierarchy">
            <div>Driver &nbsp; ></div>
            <div>&nbsp; 운전자 정보</div>
        </div>
        <div class="gs-bundle-of-buttons" style="max-height:10vh;">
            <v-btn @click="addNewRow" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                <v-icon small>mdi-plus-circle-outline</v-icon>등록
            </v-btn>
            <v-btn  @click="editSelectedRow" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                <v-icon small>mdi-pencil</v-icon>수정
            </v-btn>
            <v-btn @click="updateDriverInfoDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                <v-icon small>mdi-minus-circle-outline</v-icon>운전자 정보 업데이트
            </v-btn>
            <v-dialog v-model="updateDriverInfoDialog" width="500">
                <UpdateDriverInfoCommand
                    @closeDialog="updateDriverInfoDialog = false"
                    @updateDriverInfo="updateDriverInfo"
                ></UpdateDriverInfoCommand>
            </v-dialog>
            <v-btn @click="deleteSelectedRows" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                <v-icon small>mdi-minus-circle-outline</v-icon>삭제
            </v-btn>
            <excel-export-button class="contrast-primary-text" :exportService="this.exportService" :getFlex="getFlex" />
        </div>


        <!-- the grid -->
        <wj-flex-grid
            ref="flexGrid"
            :key="value.length"
            :autoGenerateColumns="false"
            :allowAddNew="false"
            :allowDelete="true"
            :allowPinning="'SingleColumn'"
            :newRowAtTop="false"
            :showMarquee="true"
            :selectionMode="'MultiRange'"
            :validateEdits="false"
            :itemsSource="value"
            :initialized="flexInitialized"
            :selectionChanged="onSelectionChanged"
            style="margin-top:10px; max-height:65vh;"
            class="wj-felx-grid"
        >
            <wj-flex-grid-filter :filterColumns="['RowHeader','affiliation','contact','permission','isActive','userInfoId','vehicleInfoId',]" />
            <wj-flex-grid-cell-template cellType="RowHeader" v-slot="cell">{{cell.row.index + 1}}</wj-flex-grid-cell-template>
            <wj-flex-grid-column binding="affiliation" header="소속" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="contact" header="연락처" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="permission" header="권한" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="isActive" header="사용여부" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="userInfoId" header="UserInfoId" width="2*" :isReadOnly="true" align="center">
                <wj-flex-grid-cell-template cellType="Cell" v-slot="cell">   
                    <UserInfoId v-model="cell.item.userInfoId" :editMode="editMode"></UserInfoId>
                </wj-flex-grid-cell-template>
            </wj-flex-grid-column>
            <wj-flex-grid-column binding="vehicleInfoId" header="VehicleInfoId" width="2*" :isReadOnly="true" align="center">
                <wj-flex-grid-cell-template cellType="Cell" v-slot="cell">   
                    <VehicleInfoId v-model="cell.item.vehicleInfoId" :editMode="editMode"></VehicleInfoId>
                </wj-flex-grid-cell-template>
            </wj-flex-grid-column>
            <wj-flex-grid-column binding="vehicleInfoId" header="차량 정보" width="2*" :isReadOnly="true" align="center">
                <wj-flex-grid-cell-template cellType="Cell" v-slot="cell">   
                    <VehicleInfoId v-model="cell.item.vehicleInfoId" :editMode="editMode"></VehicleInfoId>
                    {{ cell.item. }}
                </wj-flex-grid-cell-template>
            </wj-flex-grid-column>
            <wj-flex-grid-column binding="userInfoId" header="사용자 정보" width="2*" :isReadOnly="true" align="center">
                <wj-flex-grid-cell-template cellType="Cell" v-slot="cell">   
                    <UserInfoId v-model="cell.item.userInfoId" :editMode="editMode"></UserInfoId>
                    {{ cell.item. }}
                </wj-flex-grid-cell-template>
            </wj-flex-grid-column>
        </wj-flex-grid>
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <template>
                    <v-card>
                        <v-toolbar
                            color="primary"
                            class="elevation-0"
                            height="50px"
                        >
                            <div style="color:white; font-size:17px; font-weight:700;">운전자 정보 등록</div>
                            <v-spacer></v-spacer>
                            <v-icon
                                color="white"
                                small
                                @click="closeDialog()"
                            >mdi-close</v-icon>
                        </v-toolbar>
                        <v-card-text>
                            <DriverInfo :offline="offline"
                                :isNew="!itemToEdit"
                                :editMode="true"
                                v-model="newValue"
                                @add="append"
                                @edit="edit"
                            />
                        </v-card-text>
                    </v-card>
                </template>
            </v-dialog>
        </v-col>
        <v-col style="margin-bottom:40px;">
            <div class="text-center">
                <v-dialog
                    width="332.5"
                    fullscreen
                    hide-overlay
                    transition="dialog-bottom-transition"
                >
                    <v-btn
                        style="position:absolute; top:2%; right:2%"
                        @click="closeDialog()"
                        depressed
                        icon 
                        absolute
                    >
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-dialog>
            </div>
        </v-col>
    </div>
</template>

<script>
import BaseGrid from '../base-ui/BaseGrid'

export default {
    name: 'driverInfoGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'driverInfos',
        updateDriverInfoDialog: false,
    }),
    watch: {
    },
    methods:{
        updateDriverInfo(params){
            try{
                this.repository.invoke(this.getSelectedItem(), "updateDriverInfo", params)
                this.$EventBus.$emit('show-success','UpdateDriverInfo 성공적으로 처리되었습니다.')
                this.updateDriverInfoDialog = false
            }catch(e){
                this.$EventBus.$emit('show-error', e);
            }
            
        },
    }
}
</script>