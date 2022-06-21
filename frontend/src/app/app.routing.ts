import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CommunityPageComponent } from "./pages/community-page/community-page.component";
import { HomepageComponent } from "./pages/homepage/homepage.component";
import { ProfilePageComponent } from "./pages/profile-page/profile-page.component";

const routes: Routes = [
    {path: "", redirectTo: "/home", pathMatch: "full"},
    {path: "home", component: HomepageComponent},
    {path: "community", component: CommunityPageComponent},
    {path: "profile", component: ProfilePageComponent}
];
@NgModule({
    declarations: [],
    imports: [CommonModule, RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule{

}